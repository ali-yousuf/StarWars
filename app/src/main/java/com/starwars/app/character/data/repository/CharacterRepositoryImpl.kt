package com.starwars.app.character.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.starwars.app.character.data.datasource.remote.CharacterRemoteDataSource
import com.starwars.app.character.data.repository.paging.CharacterPagingSource
import com.starwars.app.character.domain.entity.Character
import com.starwars.app.character.domain.repository.CharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 10

class CharacterRepositoryImpl @Inject constructor(
    private val characterRemoteDataSource: CharacterRemoteDataSource
): CharacterRepository {
    override suspend fun getCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                CharacterPagingSource(characterRemoteDataSource)
            }
        ).flow
    }
}