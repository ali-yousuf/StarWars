package com.starwars.app.planet.data

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.starwars.app.core.db.StarWarsDatabase
import com.starwars.app.planet.data.datasource.local.PlanetDao
import com.starwars.app.planet.data.datasource.remote.PlanetRemoteDataSource
import com.starwars.app.planet.data.repository.PlanetRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import com.starwars.app.planet.domain.entity.Planet
import junit.framework.TestCase.assertNotNull
import org.mockito.Mockito.mock

class PlanetRepositoryImplTest {

    private lateinit var planetRepository: PlanetRepositoryImpl
    private lateinit var planetRemoteDataSource: PlanetRemoteDataSource
    private lateinit var starWarsDatabase: StarWarsDatabase
    private lateinit var planetDao: PlanetDao

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        planetRemoteDataSource = mock(PlanetRemoteDataSource::class.java)
        starWarsDatabase = mock(StarWarsDatabase::class.java)
        planetDao = mock(PlanetDao::class.java)
        planetRepository = PlanetRepositoryImpl(planetRemoteDataSource, starWarsDatabase)

        // Mock the behavior of starWarsDatabase to return planetDao
        Mockito.`when`(starWarsDatabase.planetDao()).thenReturn(planetDao)
    }

    @Test
    fun getPlanets_returnsPagingData() = runBlocking {
        val pagingSource: PagingSource<Int, Planet> = mock(PagingSource::class.java) as PagingSource<Int, Planet>
        Mockito.`when`(planetDao.getAll()).thenReturn(pagingSource)

        val result: Flow<PagingData<Planet>> = planetRepository.getPlanets()

        // Verify that the function behaves as expected
        assertNotNull(result)
    }
}