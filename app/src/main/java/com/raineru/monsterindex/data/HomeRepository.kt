package com.raineru.monsterindex.data

import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun fetchPokemonList(page: Int): Flow<List<Pokemon>>
}