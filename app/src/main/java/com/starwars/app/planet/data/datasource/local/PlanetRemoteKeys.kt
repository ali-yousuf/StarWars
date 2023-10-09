package com.starwars.app.planet.data.datasource.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "planet_remote_keys")
data class PlanetRemoteKeys(
    @PrimaryKey
    val planetId: String,
    val prevKey: Int?,
    val nextKey: Int?
)