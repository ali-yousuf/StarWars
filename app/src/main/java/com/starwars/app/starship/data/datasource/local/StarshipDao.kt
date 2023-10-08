package com.starwars.app.starship.data.datasource.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.starwars.app.starship.domain.entity.Starship

@Dao
interface StarshipDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(starships: List<Starship>)

    @Query("SELECT * FROM starships")
    fun getAll(): PagingSource<Int, Starship>

    @Query("DELETE FROM starships")
    suspend fun clearStarships()
}