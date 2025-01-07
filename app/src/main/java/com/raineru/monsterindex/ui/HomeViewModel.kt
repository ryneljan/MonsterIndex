package com.raineru.monsterindex.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raineru.monsterindex.data.HomeRepository
import com.raineru.monsterindex.data.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    val pokemonFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    @OptIn(ExperimentalCoroutinesApi::class)
    val pokemonList: StateFlow<List<Pokemon>> = pokemonFetchingIndex.flatMapLatest { index ->
        homeRepository.fetchPokemonList(index + 1)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun fetchNextPokemonList() {
        pokemonFetchingIndex.update {
            it + 1
        }
    }
}