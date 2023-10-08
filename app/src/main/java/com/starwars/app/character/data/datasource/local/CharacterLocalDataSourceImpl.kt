package com.starwars.app.character.data.datasource.local

import androidx.paging.PagingSource
import com.starwars.app.character.data.datasource.local.db.CharacterRemoteKeys
import com.starwars.app.character.domain.entity.Character
import com.starwars.app.core.db.StarWarsDatabase

class CharacterLocalDataSourceImpl(
    private val starWarsDatabase: StarWarsDatabase
) : CharacterLocalDataSource {

    private val characterDao = starWarsDatabase.characterDao()
    private val characterRemoteKeysDao = starWarsDatabase.characterRemoteKeysDao()

    override fun getCharacters(): PagingSource<Int, Character> {
        return characterDao.getAll()
    }

    override suspend fun insertAll(remoteKey: List<CharacterRemoteKeys>) {
        characterRemoteKeysDao.insertAll(remoteKey)
    }

    override suspend fun remoteKeysCharacterId(characterId: String): CharacterRemoteKeys? {
        return characterRemoteKeysDao.remoteKeysCharacterId(characterId)
    }

    override suspend fun clearRemoteKeys() {
        characterRemoteKeysDao.clearRemoteKeys()
    }
}