package com.starwars.app.character.data.datasource.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_remote_keys")
data class CharacterRemoteKeys(
    @PrimaryKey val characterId: String,
    val prevKey: Int?,
    val nextKey: Int?
)