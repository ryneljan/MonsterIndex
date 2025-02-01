package com.raineru.monsterindex.data

import com.raineru.monsterindex.model.PokemonInfo
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    fun fetchPokemonInfo(id: Int): Flow<PokemonInfo>
}