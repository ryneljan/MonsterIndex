package com.raineru.monsterindex

import android.util.Log
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toLowerCase
import com.raineru.monsterindex.data.Pokemon
import com.raineru.monsterindex.data.PokemonListResponse
import com.raineru.monsterindex.model.PokemonInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.io.IOException
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
        try {
            val response: PokemonListResponse = client.get(
                "https://pokeapi.co/api/v2/pokemon?offset=$offset&limit=$PAGING_SIZE"
            ).body()
            return response.results
        } catch (e: IOException) {
            Log.d("PokeApiClient", e.toString())
            return emptyList()
        }
    }

    suspend fun getPokemonInfo(id: Int): PokemonInfo? {
        try {
            val response: PokemonInfo = client.get(
                "https://pokeapi.co/api/v2/pokemon/$id"
            ).body()
            return response
        } catch (e: IOException) {
            Log.d("PokeApiClient", e.toString())
            return null
        }
    }

    companion object {
        private const val PAGING_SIZE = 20
    }
}