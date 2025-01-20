package com.raineru.monsterindex.db

import com.raineru.monsterindex.data.Pokemon
import com.raineru.monsterindex.db.entity.PokemonEntity

object PokemonEntityMapper {

    fun asEntity(domain: List<Pokemon>): List<PokemonEntity> {
        return domain.map {
            PokemonEntity(
                page = it.page,
                name = it.name,
                url = it.url,
                id = it.id
            )
        }
    }

    fun asDomain(entity: List<PokemonEntity>): List<Pokemon> {
        return entity.map {
            Pokemon(
                page = it.page,
                nameField = it.name,
                url = it.url,
            )
        }
    }
}

fun List<PokemonEntity>.asDomain(): List<Pokemon> {
    return PokemonEntityMapper.asDomain(this)
}

fun List<Pokemon>.asEntity(): List<PokemonEntity> {
    return PokemonEntityMapper.asEntity(this)
}