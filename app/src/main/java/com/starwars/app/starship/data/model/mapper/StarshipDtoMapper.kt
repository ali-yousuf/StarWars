package com.starwars.app.starship.data.model.mapper


import com.starwars.app.starship.data.model.dto.Result
import com.starwars.app.starship.domain.entity.Starship
import java.util.UUID

fun Result.mapFromEntity() = Starship(
    id = UUID.randomUUID().toString(),
    cargoCapacity = this.cargoCapacity,
    crew = this.crew,
    hyperdriveRating = this.hyperdriveRating,
    length = this.length,
    mGLT = this.mGLT,
    manufacturer = this.manufacturer,
    model = this.model,
    name = this.name,
    passengers = this.passengers,
    starshipClass = this.starshipClass
)

fun List<Result>.mapFromListModel(): List<Starship>{
    return this.map{
        it.mapFromEntity()
    }
}