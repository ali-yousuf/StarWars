package com.starwars.app.starship.data.datasource.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "starship_remote_keys")
data class StarshipRemoteKeys (
    @PrimaryKey
    val starshipId: String,
    val prevKey: Int?,
    val nextKey: Int?
)