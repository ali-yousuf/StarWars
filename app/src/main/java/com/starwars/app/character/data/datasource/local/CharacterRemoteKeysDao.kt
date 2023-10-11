package com.starwars.app.character.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<CharacterRemoteKeys>)

    @Query("SELECT * FROM character_remote_keys WHERE characterId = :characterId")
    suspend fun remoteKeysCharacterId(characterId: String): CharacterRemoteKeys?

    @Query("DELETE FROM character_remote_keys")
    suspend fun clearRemoteKeys()
}