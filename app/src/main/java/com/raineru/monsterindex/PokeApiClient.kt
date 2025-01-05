package com.raineru.monsterindex

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.raineru.monsterindex.data.Pokemon
import com.raineru.monsterindex.data.PokemonListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class PokeApiClient @Inject constructor(
    private val client: HttpClient
) {

    suspend fun getPokemon(name: String): String {
        val n = name.toLowerCase(Locale.current)
        val response: Pokemon = client.get("https://pokeapi.co/api/v2/pokemon/$n").body()
        return response.name
    }

    suspend fun getPokemon(index: Int): Pokemon {
        val response: Pokemon = client.get("https://pokeapi.co/api/v2/$index").body()
        return response
    }

    suspend fun getPokemonList(index: Int): List<Pokemon> {
        val offset = index * PAGING_SIZE
        val response: PokemonListResponse = client.get(
            "https://pokeapi.co/api/v2/pokemon?offset=$offset&limit=$PAGING_SIZE"
        ).body()
        return response.results
    }

    companion object {
        private const val PAGING_SIZE = 20
    }
}