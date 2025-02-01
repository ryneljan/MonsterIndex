package com.raineru.monsterindex.data

import com.raineru.monsterindex.PokeApiClient
import com.raineru.monsterindex.db.PokemonInfoDao
import com.raineru.monsterindex.db.asDomain
import com.raineru.monsterindex.db.asEntity
import com.raineru.monsterindex.db.entity.PokemonInfoEntity
import com.raineru.monsterindex.model.PokemonInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DetailsRepositoryImpl @Inject constructor(
    private val pokeApiClient: PokeApiClient,
    private val pokemonInfoDao: PokemonInfoDao
): DetailsRepository {

    override fun fetchPokemonInfo(id: Int): Flow<PokemonInfo> = flow {
        val pokemonInfoEntity: PokemonInfoEntity? = pokemonInfoDao.getPokemonInfo(id)

        if (pokemonInfoEntity != null) {
            emit(pokemonInfoEntity.asDomain())
        } else {
            val pokemonInfo: PokemonInfo? = pokeApiClient.getPokemonInfo(id)

            pokemonInfo?.let {
                pokemonInfoDao.insertPokemonInfo(pokemonInfo.asEntity())
                emit(pokemonInfo)
            }
        }
    }
}
