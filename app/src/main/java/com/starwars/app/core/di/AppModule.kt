package com.starwars.app.core.di

import android.app.Application
import androidx.room.Room
import com.starwars.app.core.db.StarWarsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesStarWarDatabase(application: Application): StarWarsDatabase {
        return Room.databaseBuilder(
            application,
            StarWarsDatabase::class.java,
            StarWarsDatabase.DATABASE_NAME
        ).build()
    }

}