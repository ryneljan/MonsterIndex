package com.raineru.monsterindex

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.raineru.monsterindex.data.Pokemon
import com.raineru.monsterindex.data.PokemonType
import com.raineru.monsterindex.data.toBaseStatList
import com.raineru.monsterindex.ui.BaseStatItem
import com.raineru.monsterindex.ui.DetailsViewModel
import com.raineru.monsterindex.ui.HomeViewModel
import com.raineru.monsterindex.ui.theme.MonsterIndexTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MonsterIndexTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = HomeRoute
                ) {
                    composable<HomeRoute> {
                        HomeScreen(
                            onNavigateToDetail = {
                                navController.navigate(PokemonDetailRoute(it))
                            }
                        )
                    }

                    composable<PokemonDetailRoute>(
                        typeMap = mapOf(
                            typeOf<Pokemon>() to PokemonType
                        )
                    ) { backStackEntry ->
                        val route: PokemonDetailRoute = backStackEntry.toRoute()

                        val detailsViewModel: DetailsViewModel = hiltViewModel()
                        val pokemonInfo by detailsViewModel.pokemonInfo.collectAsStateWithLifecycle()

                        LaunchedEffect(Unit) {
                            detailsViewModel.setPokemonId(route.pokemon.id)
                        }

                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    title = { Text("#${route.pokemon.id}") }
                                )
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(it),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                pokemonInfo?.let { info ->

                                    GlideImage(
                                        model = route.pokemon.imageUrl,
                                        contentDescription = route.pokemon.name,
                                        modifier = Modifier.size(200.dp)
                                    )

                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        info.types.forEach { type ->
                                            FilterChip(
                                                selected = true,
                                                onClick = {},
                                                label = {
                                                    Text(
                                                        text = type.type.name.replaceFirstChar { theChar ->
                                                            theChar.uppercase()
                                                        }
                                                    )
                                                }
                                            )
                                        }
                                    }
                                    Text(
                                        text = info.name.replaceFirstChar { theChar ->
                                            theChar.uppercase()
                                        },
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 28.sp
                                    )

                                    Spacer(modifier = Modifier.height(16.dp))

                                    Row {
                                        Text(text = "Weight: ${info.weightInKilogram} KG")
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(text = "Height: ${info.heightInMeter} M")
                                    }

                                    Spacer(modifier = Modifier.height(32.dp))

                                    Column(
                                        modifier = Modifier
                                            .width(300.dp)
                                    ) {
                                        Text(
                                            text = "Base Stats",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp,
                                        )

                                        Spacer(modifier = Modifier.height(4.dp))

                                        info.toBaseStatList().forEach { baseStat ->
                                            BaseStatItem(baseStat = baseStat)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PokemonDetailScreen(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    // TODO fetch detail from the api then cache
    // TODO create a type converter to store stats and pokemon types
    // the example from pokedex compose, they store it in json form
    Card(
        modifier = modifier,
        onClick = {
            Log.d(
                "MainActivity",
                "onClick: $pokemon, name: ${pokemon.name}, imageUrl: ${pokemon.imageUrl}, id: ${pokemon.id}"
            )
        }
    ) {
        Column(
            modifier = Modifier
                .size(400.dp)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.background(Color.Red)
            )
            Text(
                pokemon.name,
            )
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
fun HomeScreen(
    onNavigateToDetail: (Pokemon) -> Unit,
    modifier: Modifier = Modifier
) {
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
            val threshold = 16
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
                            onNavigateToDetail(it)
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

@Serializable
object HomeRoute

@Serializable
data class PokemonDetailRoute(val pokemon: Pokemon)