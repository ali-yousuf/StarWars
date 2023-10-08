package com.starwars.app.character.data.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.starwars.app.character.data.datasource.local.db.CharacterRemoteKeys
import com.starwars.app.character.data.datasource.remote.CharacterRemoteDataSource
import com.starwars.app.character.data.model.maper.mapFromListModel
import com.starwars.app.character.domain.entity.Character
import com.starwars.app.core.db.StarWarsDatabase
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val starWarsDatabase: StarWarsDatabase,
    private val remoteDataSource: CharacterRemoteDataSource
) : RemoteMediator<Int, Character>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
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
            val apiResponse = remoteDataSource.getCharacter(page)

            val apiResults = apiResponse.results
            val endOfPaginationReached = apiResults.isEmpty()
            starWarsDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    starWarsDatabase.characterRemoteKeysDao().clearRemoteKeys()
                    starWarsDatabase.characterDao().clearCharacters()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1

                val characters = apiResults.mapFromListModel()
                val keys = characters.map {
                    CharacterRemoteKeys(characterId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                starWarsDatabase.characterRemoteKeysDao().insertAll(keys)
                starWarsDatabase.characterDao().insertAll(characters)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Character>,
    ): CharacterRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                starWarsDatabase.characterRemoteKeysDao().remoteKeysCharacterId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Character>,
    ): CharacterRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->
                starWarsDatabase.characterRemoteKeysDao().remoteKeysCharacterId(character.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Character>,
    ): CharacterRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->
                starWarsDatabase.characterRemoteKeysDao().remoteKeysCharacterId(character.id)
            }
    }

}