package com.starwars.app.starship.domain.usecase

import androidx.paging.PagingData
import com.starwars.app.core.usecase.BaseUseCase
import com.starwars.app.starship.domain.entity.Starship
import com.starwars.app.starship.domain.repository.StarshipRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStarshipUseCase @Inject constructor (
    private val repository: StarshipRepository
) : BaseUseCase<Unit, Flow<PagingData<Starship>>> {
    override suspend fun execute(input: Unit): Flow<PagingData<Starship>> {
        return  repository.getStarships()
    }
}