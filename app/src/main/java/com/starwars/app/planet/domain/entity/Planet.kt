package com.starwars.app.planet.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planets")
data class Planet(
    @PrimaryKey
    var id: String = "",
    val climate: String?,
    val created: String?,
    val diameter: String?,
    val gravity: String?,
    val name: String?,
    val orbitalPeriod: String?,
    val population: String?,
    val rotationPeriod: String?,
    val surfaceWater: String?,
    val terrain: String?,
)
