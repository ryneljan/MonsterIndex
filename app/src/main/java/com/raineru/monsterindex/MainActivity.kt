package com.raineru.monsterindex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.raineru.monsterindex.ui.HomeViewModel
import com.raineru.monsterindex.ui.theme.MonsterIndexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalGlideComposeApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MonsterIndexTheme {
                /*val navController = rememberNavController()
                NavHost(navController, startDestination = "ExampleRoute") {
                    composable("ExampleRoute") {
                        val homeViewModel: HomeViewModel = hiltViewModel()
                    }
                }*/
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    /*val scope = rememberCoroutineScope()
                    var text by remember { mutableStateOf("Loading") }
                    LaunchedEffect(true) {
                        scope.launch {
                            text = try {
                                pokeApiClient.getPokemonList(1)
                                    .mapIndexed { i, p ->
                                        "${i + 1}: ${p.name}"
                                    }.toString()
                            } catch (e: Exception) {
                                e.localizedMessage ?: "error"
                            }
                        }
                    }*/

                    val homeViewModel: HomeViewModel = hiltViewModel()
                    val pokemonList by homeViewModel.pokemonList.collectAsStateWithLifecycle()
                    val pokemonFetchingIndex by homeViewModel.pokemonFetchingIndex.collectAsStateWithLifecycle()

                    Column(modifier = Modifier.padding(it)) {

                        Button(onClick = { homeViewModel.fetchNextPokemonList() }) {
                            Text(text = "Page ${pokemonFetchingIndex + 1}")
                        }
                        LazyColumn {
                            items(pokemonList) {
                                ListItem(
                                    headlineContent = { Text(it.name) },
                                    leadingContent = {
                                        GlideImage(
                                            model = it.imageUrl,
                                            contentDescription = it.name,
                                            modifier = Modifier.size(100.dp)
                                        )
                                    },
                                    modifier = Modifier.clickable {  }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}