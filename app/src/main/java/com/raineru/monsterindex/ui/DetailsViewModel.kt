package com.raineru.monsterindex.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.raineru.monsterindex.data.DetailsRepository
import com.raineru.monsterindex.model.PokemonInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository
) : ViewModel() {

    private val pokemonId: MutableStateFlow<Int> = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val pokemonInfo: StateFlow<PokemonInfo?> =
        pokemonId
            .filter { it > 0 }
            .flatMapLatest {
                repository.fetchPokemonInfo(it)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null
            )

    fun setPokemonId(id: Int) {
        pokemonId.update { id }
    }
}