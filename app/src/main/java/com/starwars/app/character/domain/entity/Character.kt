package com.starwars.app.character.domain.entity
data class Character(
    val birthYear: String,
    val eyeColor: String,
    val films: List<String>,
    val gender: String,
    val hairColor: String,
    val height: String,
    val name: String,
    val skinColor: String,
    val species: List<String>,
    val starships: List<String>,
    val vehicles: List<String>
)
