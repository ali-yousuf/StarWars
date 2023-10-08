package com.starwars.app.starship.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.starwars.app.core.db.StarWarsDatabase
import com.starwars.app.starship.data.datasource.remote.StarshipRemoteDataSource
import com.starwars.app.starship.domain.entity.Starship
import com.starwars.app.starship.domain.repository.StarshipRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 10
class StarshipRepositoryImpl @Inject constructor(
    private val starshipRemoteDataSource: StarshipRemoteDataSource,
    private val starWarsDatabase: StarWarsDatabase
): StarshipRepository {
    override suspend fun getStarships(): Flow<PagingData<Starship>> {
        val pagingSourceFactory = { starWarsDatabase.starshipDao().getAll() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = StarshipRemoteMediator(
                starWarsDatabase,
                starshipRemoteDataSource
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}