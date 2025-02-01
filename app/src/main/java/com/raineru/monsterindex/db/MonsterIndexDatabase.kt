package com.raineru.monsterindex.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.raineru.monsterindex.db.entity.PokemonEntity
import com.raineru.monsterindex.db.entity.PokemonInfoEntity

@Database(
    entities = [PokemonEntity::class, PokemonInfoEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StatsResponseConverter::class, TypeResponseConverter::class)
abstract class MonsterIndexDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    abstract fun pokemonInfoDao(): PokemonInfoDao
}