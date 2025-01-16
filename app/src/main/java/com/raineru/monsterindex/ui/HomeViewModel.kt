package com.raineru.monsterindex.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raineru.monsterindex.data.HomeRepository
import com.raineru.monsterindex.data.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val pokemonFetchingIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    @OptIn(ExperimentalCoroutinesApi::class)
    val pokemonList: StateFlow<List<Pokemon>> = pokemonFetchingIndex.flatMapLatest { index ->
        _isLoading.update { true }
        val pokemons: Flow<List<Pokemon>> = homeRepository.fetchPokemonList(index + 1)
        _isLoading.update { false }
        pokemons
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun fetchNextPokemonList() {
        if (_isLoading.value) return

        pokemonFetchingIndex.update {
            it + 1
        }
    }
}

sealed class HomeScreenUiState {
    data object Loading : HomeScreenUiState()
    data object Idle : HomeScreenUiState()
//    data class Error(val message: String?) : HomeScreenUiState()
}