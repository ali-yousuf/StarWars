package com.starwars.app.character.data.datasource.remote

import com.starwars.app.character.data.model.dto.CharacterDto
import com.starwars.app.core.network.StarWarsApi
import javax.inject.Inject

class CharacterRemoteDataSourceImpl @Inject constructor(
    private val api: StarWarsApi
) : CharacterRemoteDataSource{
    override suspend fun getCharacter(page: Int): CharacterDto {
        return api.getCharacter(page)
    }
}