package com.raineru.monsterindex.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.raineru.monsterindex.db.entity.PokemonEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    pager: Pager<Int, PokemonEntity>
) : ViewModel() {

    val pokemonListViaPaging = pager
        .flow
        .cachedIn(viewModelScope)
}
