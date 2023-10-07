package com.starwars.app.core.di

import com.starwars.app.character.data.datasource.remote.CharacterRemoteDataSource
import com.starwars.app.character.data.datasource.remote.CharacterRemoteDataSourceImpl
import com.starwars.app.character.data.repository.CharacterRepositoryImpl
import com.starwars.app.character.domain.repository.CharacterRepository
import com.starwars.app.character.domain.usecase.GetCharactersUseCase
import com.starwars.app.core.network.StarWarsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharacterModule {
    @Singleton
    @Provides
    fun providesCharacterRemoteDataSource(
        api: StarWarsApi
    ): CharacterRemoteDataSource {
        return CharacterRemoteDataSourceImpl(api)
    }

    @Singleton
    @Provides
    fun providesCharacterRepository(
        characterRemoteDataSource: CharacterRemoteDataSource
    ): CharacterRepository {
        return CharacterRepositoryImpl(characterRemoteDataSource)
    }

    @Singleton
    @Provides
    fun providesGetCharactersUseCase(
        characterRepository: CharacterRepository
    ): GetCharactersUseCase {
        return GetCharactersUseCase(characterRepository)
    }
}