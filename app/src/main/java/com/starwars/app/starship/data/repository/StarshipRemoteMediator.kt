package com.starwars.app.starship.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.starwars.app.core.db.StarWarsDatabase
import com.starwars.app.starship.data.datasource.local.StarshipRemoteKeys
import com.starwars.app.starship.data.datasource.remote.StarshipRemoteDataSource
import com.starwars.app.starship.data.model.mapper.mapFromListModel
import com.starwars.app.starship.domain.entity.Starship
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class StarshipRemoteMediator (
    private val starWarsDatabase: StarWarsDatabase,
    private val remoteDataSource: StarshipRemoteDataSource
) : RemoteMediator<Int, Starship>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Starship>
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
            val apiResponse = remoteDataSource.getStarship(page)

            val apiResults = apiResponse.results
            val endOfPaginationReached = apiResults.isEmpty()
            starWarsDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    starWarsDatabase.starshipRemoteKeysDao().clearRemoteKeys()
                    starWarsDatabase.starshipDao().clearStarships()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val starships = apiResults.mapFromListModel()
                val keys = starships.map {
                    StarshipRemoteKeys(starshipId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                starWarsDatabase.starshipRemoteKeysDao().insertAll(keys)
                starWarsDatabase.starshipDao().insertAll(starships)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Starship>,
    ): StarshipRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                starWarsDatabase.starshipRemoteKeysDao().getRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Starship>,
    ): StarshipRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { starship ->
                starWarsDatabase.starshipRemoteKeysDao().getRemoteKeys(starship.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Starship>,
    ): StarshipRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { starship ->
                starWarsDatabase.starshipRemoteKeysDao().getRemoteKeys(starship.id)
            }
    }
}