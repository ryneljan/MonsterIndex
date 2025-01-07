package com.raineru.monsterindex.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raineru.monsterindex.db.entity.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MonsterIndexDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
}