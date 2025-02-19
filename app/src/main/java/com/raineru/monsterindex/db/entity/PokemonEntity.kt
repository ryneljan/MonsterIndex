package com.raineru.monsterindex.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemons"
)
data class PokemonEntity(
    val page: Int,
    val name: String,
    val url: String,
    @PrimaryKey val id: Int
)
