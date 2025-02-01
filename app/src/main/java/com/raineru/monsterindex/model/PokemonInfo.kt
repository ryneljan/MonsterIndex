package com.raineru.monsterindex.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonInfo(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,

    @SerialName(value = "base_experience")
    val baseExperience: Int,

    @SerialName(value = "stats")
    val stats: List<StatsResponse>,

    @SerialName(value = "types")
    val types: List<TypeResponse>
) {
    @Serializable
    data class StatsResponse(
        @SerialName(value = "base_stat") val baseStat: Int,
        @SerialName(value = "stat") val stat: Stat,
    )

    @Serializable
    data class Stat(
        @SerialName(value = "name")
        val name: String
    )

    @Serializable
    data class TypeResponse(
        @SerialName(value = "slot") val slot: Int,
        @SerialName(value = "type") val type: Type
    )

    @Serializable
    data class Type(
        @SerialName(value = "name") val name: String,
    )
}
