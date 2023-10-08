package com.starwars.app.character.data.model.maper

import com.starwars.app.character.data.model.dto.Result
import com.starwars.app.character.domain.entity.Character
import java.util.UUID

fun Result.mapFromEntity() = Character(
    id = UUID.randomUUID().toString(),
    birthYear = this.birthYear,
    eyeColor = this.eyeColor,
    gender = this.gender,
    hairColor = this.hairColor,
    height = this.height,
    name = this.name,
    skinColor = this.skinColor
)

fun List<Result>.mapFromListModel(): List<Character>{
    return this.map{
        it.mapFromEntity()
    }
}