package com.raineru.monsterindex

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.raineru.monsterindex.db.MonsterIndexDatabase
import com.raineru.monsterindex.db.asEntity
import com.raineru.monsterindex.db.entity.PokemonEntity

@OptIn(ExperimentalPagingApi::class)
class MonsterIndexRemoteMediator(
    private val db: MonsterIndexDatabase,
    private val pokeApiClient: PokeApiClient
) : RemoteMediator<Int, PokemonEntity>() {

    private val pokemonDao = db.pokemonDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    pokemonDao.getPokemonWithGreatestId()?.id ?: 0
                }

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    pokemonDao.getPokemonWithGreatestId()?.id ?: 0
                }
            }

            val pokemons = pokeApiClient.getPokemonListByIndex(loadKey)

            pokemonDao.insertPokemonList(pokemons.asEntity())

            return MediatorResult.Success(endOfPaginationReached = pokemons.isEmpty())
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction = InitializeAction.SKIP_INITIAL_REFRESH
}
