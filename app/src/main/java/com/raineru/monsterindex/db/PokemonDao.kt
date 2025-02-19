package com.raineru.monsterindex.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raineru.monsterindex.db.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonList(pokemonList: List<PokemonEntity>)

    @Query("SELECT * FROM pokemons WHERE page = :page ORDER BY id ASC")
    suspend fun getPokemonList(page: Int): List<PokemonEntity>

    @Query("SELECT * FROM pokemons WHERE page <= :page ORDER BY id ASC")
    suspend fun getAllPokemonList(page: Int): List<PokemonEntity>

    @Query("DELETE FROM pokemons")
    suspend fun clearAllPokemons()

    // Used by paging library
    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    fun pagingSource(): PagingSource<Int, PokemonEntity>

    // Return the last id of the last fetched Pokemon from the API
    @Query("SELECT * FROM pokemons ORDER BY id DESC LIMIT 1")
    suspend fun getPokemonWithGreatestId(): PokemonEntity?
}