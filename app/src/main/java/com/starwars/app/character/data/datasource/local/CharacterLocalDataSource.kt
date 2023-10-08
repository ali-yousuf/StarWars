package com.starwars.app.character.data.datasource.local

import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.starwars.app.character.data.datasource.local.db.CharacterRemoteKeys
import com.starwars.app.character.domain.entity.Character

interface CharacterLocalDataSource {
    fun getCharacters(): PagingSource<Int, Character>

    suspend fun insertAll(remoteKey: List<CharacterRemoteKeys>)

    suspend fun remoteKeysCharacterId(characterId: String): CharacterRemoteKeys?

    suspend fun clearRemoteKeys()
}