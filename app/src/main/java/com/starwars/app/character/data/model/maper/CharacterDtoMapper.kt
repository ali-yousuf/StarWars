package com.starwars.app.character.data.model.maper

import com.starwars.app.character.data.model.dto.Result
import com.starwars.app.character.domain.entity.Character

fun Result.mapFromEntity() = Character(
    birthYear = this.birthYear,
    eyeColor = this.eyeColor,
    films = this.films,
    gender = this.gender,
    hairColor = this.hairColor,
    height = this.height,
    name = this.name,
    skinColor = this.skinColor,
    species = this.species,
    starships = this.starships,
    vehicles = this.vehicles
)

fun List<Result>.mapFromListModel(): List<Character>{
    return this.map{
        it.mapFromEntity()
    }
}