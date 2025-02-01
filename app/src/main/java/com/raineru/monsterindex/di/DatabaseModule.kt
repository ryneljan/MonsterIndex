package com.raineru.monsterindex.di

import android.content.Context
import androidx.room.Room
import com.raineru.monsterindex.db.MonsterIndexDatabase
import com.raineru.monsterindex.db.PokemonDao
import com.raineru.monsterindex.db.PokemonInfoDao
import com.raineru.monsterindex.db.StatsResponseConverter
import com.raineru.monsterindex.db.TypeResponseConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providePokemonDao(database: MonsterIndexDatabase): PokemonDao = database.pokemonDao()

    @Provides
    @Singleton
    fun providePokemonInfoDao(database: MonsterIndexDatabase): PokemonInfoDao = database.pokemonInfoDao()

    @Provides
    @Singleton
    fun provideStatsResponseConverter(json: Json): StatsResponseConverter {
        return StatsResponseConverter(json)
    }

    @Provides
    @Singleton
    fun provideTypeResponseConverter(json: Json): TypeResponseConverter {
        return TypeResponseConverter(json)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        statsResponseConverter: StatsResponseConverter,
        typeResponseConverter: TypeResponseConverter
    ): MonsterIndexDatabase {
        return Room
            .databaseBuilder(
                context,
                MonsterIndexDatabase::class.java,
                "monster_index_db"
            )
            .fallbackToDestructiveMigration()
            .addTypeConverter(statsResponseConverter)
            .addTypeConverter(typeResponseConverter)
            .build()
    }
}