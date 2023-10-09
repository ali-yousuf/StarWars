package com.starwars.app.planet.data.model.mapper

import com.starwars.app.planet.data.model.dto.Result
import com.starwars.app.planet.domain.entity.Planet
import java.util.UUID

fun Result.mapFromEntity() = Planet(
    id = UUID.randomUUID().toString(),
    climate = this.climate,
    created = this.created,
    diameter = this.diameter,
    gravity = this.gravity,
    name = this.name,
    orbitalPeriod = this.orbitalPeriod,
    population = this.population,
    rotationPeriod = this.rotationPeriod,
    surfaceWater = this.surfaceWater,
    terrain = this.terrain
)

fun List<Result>.mapFromListModel(): List<Planet> {
    return this.map {
        it.mapFromEntity()
    }
}