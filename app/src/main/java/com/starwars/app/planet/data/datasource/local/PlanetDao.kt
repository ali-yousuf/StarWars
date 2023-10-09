package com.starwars.app.planet.data.datasource.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.starwars.app.planet.domain.entity.Planet

@Dao
interface PlanetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(planets: List<Planet>)

    @Query("SELECT * FROM planets")
    fun getAll(): PagingSource<Int, Planet>

    @Query("DELETE FROM planets")
    suspend fun clearPlanets()
}