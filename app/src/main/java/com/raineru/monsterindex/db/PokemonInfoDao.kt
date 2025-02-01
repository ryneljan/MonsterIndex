package com.raineru.monsterindex.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raineru.monsterindex.db.entity.PokemonInfoEntity

@Dao
interface PokemonInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonInfo(pokemonInfo: PokemonInfoEntity)

    @Query("SELECT * FROM pokemon_info WHERE id = :id")
    suspend fun getPokemonInfo(id: Int): PokemonInfoEntity?
}
