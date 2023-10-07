package com.starwars.app.character.presentation

import com.starwars.app.character.domain.entity.Character

data class CharacterState(
    val characters: List<Character>
)
