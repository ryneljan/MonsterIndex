package com.raineru.monsterindex.di

import com.raineru.monsterindex.data.DetailsRepository
import com.raineru.monsterindex.data.DetailsRepositoryImpl
import com.raineru.monsterindex.data.HomeRepository
import com.raineru.monsterindex.data.HomeRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun bindDetailsRepository(detailsRepositoryImpl: DetailsRepositoryImpl): DetailsRepository
}