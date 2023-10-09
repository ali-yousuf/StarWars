package com.starwars.app.planet.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlanetRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<PlanetRemoteKeys>)

    @Query("SELECT * FROM planet_remote_keys WHERE planetId = :planetId")
    suspend fun getRemoteKeys(planetId: String): PlanetRemoteKeys?

    @Query("DELETE FROM planet_remote_keys")
    suspend fun clearRemoteKeys()
}