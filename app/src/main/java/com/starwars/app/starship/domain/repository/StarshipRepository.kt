package com.starwars.app.starship.domain.repository

import androidx.paging.PagingData
import com.starwars.app.starship.domain.entity.Starship
import kotlinx.coroutines.flow.Flow

interface StarshipRepository {
    suspend fun getStarships(): Flow<PagingData<Starship>>
}