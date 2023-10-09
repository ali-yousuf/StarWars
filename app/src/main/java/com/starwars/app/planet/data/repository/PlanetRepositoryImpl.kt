package com.starwars.app.planet.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.starwars.app.core.db.StarWarsDatabase
import com.starwars.app.planet.data.datasource.remote.PlanetRemoteDataSource
import com.starwars.app.planet.domain.entity.Planet
import com.starwars.app.planet.domain.repository.PlanetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PAGE_SIZE = 10
class PlanetRepositoryImpl @Inject constructor(
    private val planetRemoteDataSource: PlanetRemoteDataSource,
    private val starWarsDatabase: StarWarsDatabase
): PlanetRepository {
    override suspend fun getPlanets(): Flow<PagingData<Planet>> {
        val pagingSourceFactory = { starWarsDatabase.planetDao().getAll() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = PlanetRemoteMediator(
                starWarsDatabase,
                planetRemoteDataSource
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}