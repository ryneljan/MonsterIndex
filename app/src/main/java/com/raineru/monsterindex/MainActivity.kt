package com.raineru.monsterindex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.raineru.monsterindex.data.Pokemon
import com.raineru.monsterindex.data.PokemonType
import com.raineru.monsterindex.data.toBaseStatList
import com.raineru.monsterindex.db.asDomain
import com.raineru.monsterindex.ui.BaseStatItem
import com.raineru.monsterindex.ui.DetailsViewModel
import com.raineru.monsterindex.ui.HomeViewModel
import com.raineru.monsterindex.ui.theme.MonsterIndexTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MonsterIndexTheme {
                MonsterIndexApp()
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MonsterIndexApp() {
    SharedTransitionLayout {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = HomeRoute
        ) {
            composable<HomeRoute> {
                HomeScreen(
                    onNavigateToDetail = {
                        navController.navigate(PokemonDetailRoute(it))
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }

            composable<PokemonDetailRoute>(
                typeMap = mapOf(
                    typeOf<Pokemon>() to PokemonType
                )
            ) { backStackEntry ->
                val route: PokemonDetailRoute = backStackEntry.toRoute()
                PokemonDetailScreen(
                    route = route,
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable
                )
            }
        }
    }
}

// TODO image animation goes above the system bars when transitioning from home to details screen
@OptIn(
    ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun PokemonDetailScreen(
    route: PokemonDetailRoute,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier
) {
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
        },
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            with(sharedTransitionScope) {
                GlideImage(
                    model = route.pokemon.imageUrl,
                    contentDescription = route.pokemon.name,
                    modifier = Modifier
                        .sharedElement(
                            rememberSharedContentState(key = "${route.pokemon.name}-image"),
                            animatedVisibilityScope = animatedContentScope
                        )
                        .size(200.dp)
                )

                Text(
                    text = route.pokemon.name.replaceFirstChar { theChar ->
                        theChar.uppercase()
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    modifier = Modifier
                        .sharedBounds(
                            rememberSharedContentState(key = "${route.pokemon.name}-text"),
                            animatedVisibilityScope = animatedContentScope
                        )
                )
            }

            pokemonInfo?.let { info ->
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

@OptIn(
    ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun HomeScreen(
    onNavigateToDetail: (Pokemon) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
    ) { padding ->
        val homeViewModel: HomeViewModel = hiltViewModel()

        val pokemonListViaPaging = homeViewModel.pokemonListViaPaging.collectAsLazyPagingItems()

        val lazyGridState = rememberLazyGridState()

        val density = LocalDensity.current
        val statusBarInset = WindowInsets.statusBars
        val appBarMaxHeightPx = remember(density) {
            with(density) {
                AppBarHeight.roundToPx() + statusBarInset.getTop(this)
            }
        }

        val connection = remember(appBarMaxHeightPx) {
            CollapsingAppBarNestedScrollConnection(
                appBarMaxHeightPx
            )
        }

        Box(
            modifier = Modifier
                .imePadding()
                .fillMaxSize()
                .nestedScroll(connection)
        ) {
            val spaceHeight by remember(density) {
                derivedStateOf {
                    with(density) {
                        (appBarMaxHeightPx + connection.appBarOffset).toDp()
                    }
                }
            }

            Column {
                Spacer(
                    modifier = Modifier
                        .height(spaceHeight)
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(
                        8.dp
                    ),
                    state = lazyGridState,
                ) {
                    items(
                        count = pokemonListViaPaging.itemCount,
                        key = pokemonListViaPaging.itemKey {
                            it.id
                        }
                    ) {
                        val pokemon = pokemonListViaPaging[it]
                        pokemon?.let { pokemonEntity ->
                            val domain = pokemonEntity.asDomain()

                            Card(
                                onClick = {
                                    onNavigateToDetail(domain)
                                },
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    with(sharedTransitionScope) {
                                        GlideImage(
                                            model = domain.imageUrl,
                                            contentDescription = domain.name,
                                            modifier = Modifier
                                                .sharedElement(
                                                    rememberSharedContentState(key = "${domain.name}-image"),
                                                    animatedVisibilityScope = animatedContentScope
                                                )
                                                .size(100.dp)
                                        )
                                        Text(
                                            pokemonEntity.name,
                                            modifier = Modifier
                                                .sharedBounds(
                                                    rememberSharedContentState(key = "${domain.name}-text"),
                                                    animatedVisibilityScope = animatedContentScope
                                                )
                                        )
                                    }
                                }
                            }
                        }
                    }
                    item {
                        Spacer(
                            modifier = Modifier
                                .windowInsetsBottomHeight(WindowInsets.systemBars)
                        )
                    }
                }

            }

            Box(
                modifier = Modifier
                    .offset { IntOffset(0, connection.appBarOffset) }
                    .border(
                        width = 1.dp,
                        color = Color.Red
                    )
                    .fillMaxWidth()
                    .height(
                        TopAppBarDefaults.TopAppBarExpandedHeight + WindowInsets.statusBars
                            .asPaddingValues()
                            .calculateTopPadding()
                    )
                    .padding(8.dp)
            ) {
                BasicTextField(
                    state = homeViewModel.textFieldState,
                    decorator = { innerTextField ->
                        Row(
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxWidth()
                        ) {
                            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                            innerTextField()
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                )
            }
        }
    }
}

@Serializable
object HomeRoute

@Serializable
data class PokemonDetailRoute(val pokemon: Pokemon)
