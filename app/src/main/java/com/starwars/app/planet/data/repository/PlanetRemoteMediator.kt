package com.starwars.app.planet.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.starwars.app.core.db.StarWarsDatabase
import com.starwars.app.planet.data.datasource.local.PlanetRemoteKeys
import com.starwars.app.planet.data.datasource.remote.PlanetRemoteDataSource
import com.starwars.app.planet.data.model.mapper.mapFromListModel
import com.starwars.app.planet.domain.entity.Planet
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PlanetRemoteMediator (
    private val starWarsDatabase: StarWarsDatabase,
    private val remoteDataSource: PlanetRemoteDataSource
) : RemoteMediator<Int, Planet>(){
    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Planet>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val apiResponse = remoteDataSource.getPlanet(page)

            val apiResults = apiResponse.results
            val endOfPaginationReached = apiResults.isEmpty()
            starWarsDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    starWarsDatabase.planetRemoteKeysDao().clearRemoteKeys()
                    starWarsDatabase.planetDao().clearPlanets()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val planets = apiResults.mapFromListModel()
                val keys = planets.map {
                    PlanetRemoteKeys(planetId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                starWarsDatabase.planetRemoteKeysDao().insertAll(keys)
                starWarsDatabase.planetDao().insertAll(planets)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Planet>,
    ): PlanetRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                starWarsDatabase.planetRemoteKeysDao().getRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Planet>,
    ): PlanetRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { planet ->
                starWarsDatabase.planetRemoteKeysDao().getRemoteKeys(planet.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Planet>,
    ): PlanetRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { planet ->
                starWarsDatabase.planetRemoteKeysDao().getRemoteKeys(planet.id)
            }
    }
}