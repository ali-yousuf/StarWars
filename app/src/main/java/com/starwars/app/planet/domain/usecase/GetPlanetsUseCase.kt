package com.starwars.app.planet.domain.usecase

import androidx.paging.PagingData
import com.starwars.app.core.usecase.BaseUseCase
import com.starwars.app.planet.domain.entity.Planet
import com.starwars.app.planet.domain.repository.PlanetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPlanetsUseCase @Inject constructor(
    private val repository: PlanetRepository
) : BaseUseCase<Unit, Flow<PagingData<Planet>>> {
    override suspend fun execute(input: Unit): Flow<PagingData<Planet>> {
        return repository.getPlanets();
    }
}