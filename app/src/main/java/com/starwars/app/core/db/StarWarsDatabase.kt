package com.starwars.app.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.starwars.app.character.data.datasource.local.db.CharacterDao
import com.starwars.app.character.data.datasource.local.db.CharacterRemoteKeys
import com.starwars.app.character.data.datasource.local.db.CharacterRemoteKeysDao
import com.starwars.app.character.domain.entity.Character
import com.starwars.app.starship.data.datasource.local.StarshipDao
import com.starwars.app.starship.data.datasource.local.StarshipRemoteKeys
import com.starwars.app.starship.data.datasource.local.StarshipRemoteKeysDao
import com.starwars.app.starship.domain.entity.Starship


@Database(
    entities = [
        Character::class,
        CharacterRemoteKeys::class,
        Starship::class,
        StarshipRemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class StarWarsDatabase: RoomDatabase() {

    abstract fun characterDao(): CharacterDao
    abstract fun characterRemoteKeysDao(): CharacterRemoteKeysDao
    abstract fun starshipDao(): StarshipDao
    abstract fun starshipRemoteKeysDao(): StarshipRemoteKeysDao

    companion object{
        const val DATABASE_NAME = "star_wars_db"
    }
}