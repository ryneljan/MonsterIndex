package com.raineru.monsterindex.db

import com.raineru.monsterindex.data.Pokemon
import com.raineru.monsterindex.db.entity.PokemonEntity

object PokemonEntityMapper {

    fun asEntity(domain: Pokemon): PokemonEntity {
        return PokemonEntity(
            page = domain.page,
            name = domain.name,
            url = domain.url,
            id = domain.id
        )
    }

    fun asDomain(entity: PokemonEntity): Pokemon {
        return Pokemon(
            page = entity.page,
            nameField = entity.name,
            url = entity.url,
        )
    }
}

fun PokemonEntity.asDomain(): Pokemon {
    return PokemonEntityMapper.asDomain(this)
}

fun Pokemon.asEntity(): PokemonEntity {
    return PokemonEntityMapper.asEntity(this)
}

fun List<PokemonEntity>.asDomain(): List<Pokemon> {
    return this.map { PokemonEntityMapper.asDomain(it) }
}

fun List<Pokemon>.asEntity(): List<PokemonEntity> {
    return this.map { PokemonEntityMapper.asEntity(it) }
}