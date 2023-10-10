package com.starwars.app.starship.data


import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.starwars.app.core.db.StarWarsDatabase
import com.starwars.app.starship.data.datasource.local.StarshipDao
import com.starwars.app.starship.data.datasource.remote.StarshipRemoteDataSource
import com.starwars.app.starship.data.repository.StarshipRepositoryImpl
import com.starwars.app.starship.domain.entity.Starship
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class StarshipRepositoryImplTest {

    private lateinit var starshipRepository: StarshipRepositoryImpl
    private lateinit var starshipRemoteDataSource: StarshipRemoteDataSource
    private lateinit var starWarsDatabase: StarWarsDatabase
    private lateinit var starshipDao: StarshipDao

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        starshipRemoteDataSource = mock(StarshipRemoteDataSource::class.java)
        starWarsDatabase = mock(StarWarsDatabase::class.java)
        starshipDao = mock(StarshipDao::class.java)
        starshipRepository = StarshipRepositoryImpl(starshipRemoteDataSource, starWarsDatabase)

        // Mock the behavior of starWarsDatabase to return starshipDao
        Mockito.`when`(starWarsDatabase.starshipDao()).thenReturn(starshipDao)
    }

    @Test
    fun getStarships_returnsPagingData() = runBlocking {
        val pagingSource: PagingSource<Int, Starship> = mock(PagingSource::class.java) as PagingSource<Int, Starship>
        Mockito.`when`(starshipDao.getAll()).thenReturn(pagingSource)

        val result: Flow<PagingData<Starship>> = starshipRepository.getStarships()

        // Verify that the function behaves as expected
        assertNotNull(result)
    }
}

