package com.raineru.monsterindex.data

import android.util.Log
import com.raineru.monsterindex.PokeApiClient
import com.raineru.monsterindex.db.PokemonDao
import com.raineru.monsterindex.db.asDomain
import com.raineru.monsterindex.db.asEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val pokeApiClient: PokeApiClient,
    private val pokemonDao: PokemonDao
) : HomeRepository {

    override fun fetchPokemonList(page: Int): Flow<List<Pokemon>> = flow {
        var pokemons: List<Pokemon> = pokemonDao.getPokemonList(page).asDomain()

        if (pokemons.isEmpty()) {
            Log.d("HomeRepositoryImpl", "fetching pokemon list: $page")
            pokemons = pokeApiClient.getPokemonList(page - 1)
            pokemons.forEach { pokemon -> pokemon.page = page }
            Log.d("HomeRepositoryImpl", "inserting pokemon list: $page")
            pokemonDao.insertPokemonList(pokemons.asEntity())
            Log.d("HomeRepositoryImpl", "emitting pokemon list: $page")
            emit(pokemonDao.getAllPokemonList(page).asDomain())
        } else {
            emit(pokemonDao.getAllPokemonList(page).asDomain())
        }
    }

}



