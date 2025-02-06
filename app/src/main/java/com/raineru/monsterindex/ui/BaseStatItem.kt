package com.raineru.monsterindex.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.raineru.monsterindex.data.BaseStat
import com.raineru.monsterindex.data.toBaseStatList
import com.raineru.monsterindex.model.PokemonInfo
import com.raineru.monsterindex.ui.components.BaseStatProgressBar

@Composable
fun BaseStatItem(
    baseStat: BaseStat,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = baseStat.type,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
        )
        BaseStatProgressBar(
            modifier = Modifier
                .fillMaxWidth(),
            progress = baseStat.progress,
            color = baseStat.color,
            label = baseStat.label,
        )
    }
}

@Preview
@Composable
fun BaseStatItemPreview() {
    Column(
        modifier = Modifier
    ) {
        bulbasaurInfo.toBaseStatList().forEach {
            BaseStatItem(baseStat = it)
        }
    }
}

private val bulbasaurInfo = PokemonInfo(
    id = 1,
    name = "Bulbasaur",
    height = 7,
    weight = 69,
    baseExperience = 64,
    stats = listOf(
        PokemonInfo.StatsResponse(
//            baseStat = 45,
            baseStat = 1,
            stat = PokemonInfo.Stat(name = "hp")
        ),
        PokemonInfo.StatsResponse(
//            baseStat = 49,
            baseStat = 20,
            stat = PokemonInfo.Stat(name = "attack")
        ),
        PokemonInfo.StatsResponse(
            baseStat = 49,
            stat = PokemonInfo.Stat(name = "defense")
        ),
        PokemonInfo.StatsResponse(
            baseStat = 65,
            stat = PokemonInfo.Stat(name = "special-attack")
        ),
        PokemonInfo.StatsResponse(
            baseStat = 65,
            stat = PokemonInfo.Stat(name = "special-defense")
        ),
        PokemonInfo.StatsResponse(
//            baseStat = 45,
            baseStat = 255,
            stat = PokemonInfo.Stat(name = "speed")
        )
    ),
    types = listOf()
)