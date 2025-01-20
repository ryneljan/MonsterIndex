package com.raineru.monsterindex

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.raineru.monsterindex.data.Pokemon
import com.raineru.monsterindex.ui.HomeViewModel
import com.raineru.monsterindex.ui.theme.MonsterIndexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MonsterIndexTheme {
                HomeScreen()
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Preview(showBackground = true)
@Composable
fun CardPreview() {
    val pokemon = Pokemon(
        nameField = "Bulbasaur",
        url = "https://pokeapi.co/api/v2/pokemon/1/"
    )

    Card {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.size(100.dp)
            )
            Text(pokemon.name)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Monster Index") }
            )
        }
    ) {
        val homeViewModel: HomeViewModel = hiltViewModel()
        val pokemonList by homeViewModel.pokemonList.collectAsStateWithLifecycle()
        val isLoading by homeViewModel.isLoading.collectAsStateWithLifecycle()

        Column(modifier = Modifier.padding(it)) {
            val lazyGridState = rememberLazyGridState()
            val threshold = 3
            val shouldLoadMore = remember {
                derivedStateOf {
                    val lastVisibleItem = lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()
                        ?: return@derivedStateOf false

                    lastVisibleItem.index >= lazyGridState.layoutInfo.totalItemsCount - 1 - threshold
                }
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp),
                state = lazyGridState
            ) {
                items(pokemonList, key = { pokemon -> pokemon.name }) {
                    Card(
                        onClick = {
//                            Log.d("MainActivity", "clicked: ${it.name}")
                        },
//                                    modifier = Modifier.wrapContentSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
//                                            .background(Color.Yellow)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            GlideImage(
                                model = it.imageUrl,
                                contentDescription = it.name,
                                modifier = Modifier.size(100.dp)
                            )
                            Text(
                                it.name,
                            )
                        }
                    }
                }
                if (shouldLoadMore.value && !isLoading) {
                    item {
                        CircularProgressIndicator()

                        LaunchedEffect(key1 = shouldLoadMore) {
//                            onLoadMore()
                            Log.d("MainActivity", "loadMore()")
                            homeViewModel.fetchNextPokemonList()
                        }
                    }
                }
            }
        }
    }
}