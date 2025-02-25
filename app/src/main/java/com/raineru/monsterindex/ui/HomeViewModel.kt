package com.raineru.monsterindex.ui

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.filter
import com.raineru.monsterindex.db.entity.PokemonEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    pager: Pager<Int, PokemonEntity>
) : ViewModel() {

    val textFieldState = TextFieldState()

    private val textFieldStateFlow = snapshotFlow { textFieldState.text }

    @OptIn(FlowPreview::class)
    val pokemonListViaPaging = pager
        .flow
        .cachedIn(viewModelScope)
        .combine(textFieldStateFlow.debounce(500)) { pagingData, query ->
            pagingData.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
        .cachedIn(viewModelScope)
}
