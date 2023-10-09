package com.starwars.app.planet.data.datasource.remote

import com.starwars.app.planet.data.model.dto.PlanetDto

interface PlanetRemoteDataSource {
    suspend fun getPlanet(page: Int): PlanetDto
}