package com.raineru.monsterindex.db

import com.raineru.monsterindex.db.entity.PokemonInfoEntity
import com.raineru.monsterindex.model.PokemonInfo

object PokemonInfoEntityMapper {

    fun asEntity(domain: PokemonInfo): PokemonInfoEntity {
        return PokemonInfoEntity(
            id = domain.id,
            name = domain.name,
            height = domain.height,
            weight = domain.weight,
            baseExperience = domain.baseExperience,
            types = domain.types,
            stats = domain.stats
        )
    }

    fun asDomain(entity: PokemonInfoEntity): PokemonInfo {
        return PokemonInfo(
            id = entity.id,
            name = entity.name,
            height = entity.height,
            weight = entity.weight,
            baseExperience = entity.baseExperience,
            types = entity.types,
            stats = entity.stats
        )
    }
}

fun PokemonInfoEntity.asDomain() = PokemonInfoEntityMapper.asDomain(this)

fun PokemonInfo.asEntity() = PokemonInfoEntityMapper.asEntity(this)