package com.raineru.monsterindex.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.raineru.monsterindex.MonsterIndexRemoteMediator
import com.raineru.monsterindex.PokeApiClient
import com.raineru.monsterindex.db.MonsterIndexDatabase
import com.raineru.monsterindex.db.entity.PokemonEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PagerModule {

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providePager(db: MonsterIndexDatabase, pokeApiClient: PokeApiClient): Pager<Int, PokemonEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = PokeApiClient.PAGING_SIZE,
            ),
            remoteMediator = MonsterIndexRemoteMediator(db, pokeApiClient),
            pagingSourceFactory = {
                db.pokemonDao().pagingSource()
            }
        )
    }
}