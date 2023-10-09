package com.starwars.app.planet.domain.repository

import androidx.paging.PagingData
import com.starwars.app.planet.domain.entity.Planet
import kotlinx.coroutines.flow.Flow

interface PlanetRepository {
    suspend fun getPlanets(): Flow<PagingData<Planet>>
}