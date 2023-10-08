package com.starwars.app.character.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.starwars.app.character.data.datasource.remote.CharacterRemoteDataSource
import com.starwars.app.character.data.repository.paging.CharacterRemoteMediator
import com.starwars.app.character.domain.entity.Character
import com.starwars.app.character.domain.repository.CharacterRepository
import com.starwars.app.core.db.StarWarsDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 10

class CharacterRepositoryImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource,
    private val starWarsDatabase: StarWarsDatabase
) : CharacterRepository {
    override suspend fun getCharacters(): Flow<PagingData<Character>> {

        val pagingSourceFactory = { starWarsDatabase.characterDao().getAll() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = CharacterRemoteMediator(
                starWarsDatabase,
                characterRemoteDataSource
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}