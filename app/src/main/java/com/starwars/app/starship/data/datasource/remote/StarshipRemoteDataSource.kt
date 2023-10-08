package com.starwars.app.starship.data.datasource.remote

import com.starwars.app.starship.data.model.dto.StarshipDto

interface StarshipRemoteDataSource {
    suspend fun getStarship(page: Int): StarshipDto
}