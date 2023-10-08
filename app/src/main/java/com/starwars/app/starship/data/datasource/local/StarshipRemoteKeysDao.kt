package com.starwars.app.starship.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface StarshipRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<StarshipRemoteKeys>)

    @Query("SELECT * FROM starship_remote_keys WHERE starshipId = :starshipId")
    suspend fun getRemoteKeys(starshipId: String): StarshipRemoteKeys?

    @Query("DELETE FROM starship_remote_keys")
    suspend fun clearRemoteKeys()
}