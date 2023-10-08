package com.starwars.app.starship.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity("starships")
data class Starship(
    @PrimaryKey
    var id: String = "",
    val cargoCapacity: String?,
    val crew: String?,
    val hyperdriveRating: String?,
    val length: String?,
    val mGLT: String?,
    val manufacturer: String?,
    val model: String?,
    val name: String?,
    val passengers: String?,
    val starshipClass: String?,
)
