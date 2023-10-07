package com.starwars.app.character.data.datasource.remote

import com.starwars.app.character.data.model.dto.CharacterDto

interface CharacterRemoteDataSource {
    suspend fun getCharacter(page: Int): CharacterDto
}