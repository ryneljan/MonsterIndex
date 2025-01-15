package com.raineru.monsterindex.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemons"
)
data class PokemonEntity(
    val page: Int,
    @PrimaryKey val name: String,
    val url: String
)
