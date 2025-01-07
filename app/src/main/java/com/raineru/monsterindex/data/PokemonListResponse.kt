package com.raineru.monsterindex.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonListResponse(
    @SerialName(value = "count") val count: Int,
    @SerialName(value = "next") val next: String?,
    @SerialName(value = "previous") val previous: String?,
    @SerialName(value = "results") val results: List<Pokemon>
)