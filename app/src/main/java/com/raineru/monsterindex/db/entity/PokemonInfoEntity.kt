package com.raineru.monsterindex.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.raineru.monsterindex.model.PokemonInfo.TypeResponse
import com.raineru.monsterindex.model.PokemonInfo.StatsResponse

@Entity(tableName = "pokemon_info")
data class PokemonInfoEntity(
    @PrimaryKey
    val id: Int,
    val name: String,

    // The height of this Pokémon in decimetres.
    val height: Int,

    // The weight of this Pokémon in hectograms.
    val weight: Int,

    val baseExperience: Int,

    val types: List<TypeResponse>,

    val stats: List<StatsResponse>
)
