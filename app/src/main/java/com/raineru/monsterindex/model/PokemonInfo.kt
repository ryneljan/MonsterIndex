package com.raineru.monsterindex.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.random.Random

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
    val hp: Int by lazy {
        stats.firstOrNull { it.stat.name == "hp" }?.baseStat ?: Random.nextInt(MAX_BASE_STAT)
    }

    val attack: Int by lazy {
        stats.firstOrNull { it.stat.name == "attack" }?.baseStat ?: Random.nextInt(MAX_BASE_STAT)
    }

    val defense: Int by lazy {
        stats.firstOrNull { it.stat.name == "defense" }?.baseStat ?: Random.nextInt(MAX_BASE_STAT)
    }

    val specialAttack: Int by lazy {
        stats.firstOrNull { it.stat.name == "special-attack" }?.baseStat ?: Random.nextInt(MAX_BASE_STAT)
    }

    val specialDefense: Int by lazy {
        stats.firstOrNull { it.stat.name == "special-defense" }?.baseStat ?: Random.nextInt(MAX_BASE_STAT)
    }

    val speed: Int by lazy {
        stats.firstOrNull { it.stat.name == "speed" }?.baseStat ?: Random.nextInt(MAX_BASE_STAT)
    }

    fun getHpString(): String = " $hp/$MAX_BASE_STAT"
    fun getAttackString(): String = " $attack/$MAX_BASE_STAT"
    fun getDefenseString(): String = " $defense/$MAX_BASE_STAT"
    fun getSpecialAttackString(): String = " $specialAttack/$MAX_BASE_STAT"
    fun getSpecialDefenseString(): String = " $specialDefense/$MAX_BASE_STAT"
    fun getSpeedString(): String = " $speed/$MAX_BASE_STAT"

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

    val weightInKilogram: Double
        get() = weight.toDouble() / 10.0

    val heightInMeter: Double
        get() = height.toDouble() / 10.0

    companion object {
        const val MAX_BASE_STAT = 255
    }
}
