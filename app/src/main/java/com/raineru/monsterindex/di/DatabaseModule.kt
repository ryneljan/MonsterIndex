package com.raineru.monsterindex.di

import android.content.Context
import androidx.room.Room
import com.raineru.monsterindex.db.MonsterIndexDatabase
import com.raineru.monsterindex.db.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePokemonDao(database: MonsterIndexDatabase): PokemonDao = database.pokemonDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MonsterIndexDatabase =
        Room.databaseBuilder(
            context,
            MonsterIndexDatabase::class.java,
            "monster_index_db"
        )
            .fallbackToDestructiveMigration()
            .build()
}