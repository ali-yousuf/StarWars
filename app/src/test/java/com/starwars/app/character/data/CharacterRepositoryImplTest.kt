package com.starwars.app.character.data

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.starwars.app.character.data.datasource.remote.CharacterRemoteDataSource
import com.starwars.app.character.data.datasource.local.CharacterDao
import com.starwars.app.character.data.repository.CharacterRepositoryImpl
import com.starwars.app.character.domain.entity.Character
import com.starwars.app.core.db.StarWarsDatabase
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class CharacterRepositoryImplTest {

    private lateinit var characterRepository: CharacterRepositoryImpl
    private lateinit var characterRemoteDataSource: CharacterRemoteDataSource
    private lateinit var starWarsDatabase: StarWarsDatabase
    private lateinit var characterDao: CharacterDao

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        characterRemoteDataSource = mock(CharacterRemoteDataSource::class.java)
        starWarsDatabase = mock(StarWarsDatabase::class.java)
        characterDao = mock(CharacterDao::class.java)
        characterRepository = CharacterRepositoryImpl(characterRemoteDataSource, starWarsDatabase)

        // Mock the behavior of starWarsDatabase to return characterDao
        `when`(starWarsDatabase.characterDao()).thenReturn(characterDao)
    }

    @Test
    fun getCharacters_returnsPagingData() = runBlocking {
        val pagingSource: PagingSource<Int, Character> = mock(PagingSource::class.java) as PagingSource<Int, Character>
        `when`(characterDao.getAll()).thenReturn(pagingSource)

        val result: Flow<PagingData<Character>> = characterRepository.getCharacters()

        // Verify that the function behaves as expected
        assertNotNull(result)
    }
}

