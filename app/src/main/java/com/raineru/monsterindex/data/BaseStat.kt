package com.raineru.monsterindex.data

import androidx.annotation.FloatRange
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.raineru.monsterindex.R
import com.raineru.monsterindex.model.PokemonInfo
import com.raineru.monsterindex.ui.theme.AttackBg
import com.raineru.monsterindex.ui.theme.DefenseBg
import com.raineru.monsterindex.ui.theme.HpBg
import com.raineru.monsterindex.ui.theme.SpecialAttackBg
import com.raineru.monsterindex.ui.theme.SpecialDefenseBg
import com.raineru.monsterindex.ui.theme.SpeedBg

data class BaseStat(
    val type: String,
    @FloatRange(from = 0.0, to = 1.0) val progress: Float,
    val color: Color,
    val label: String
)

@Composable
fun PokemonInfo.toBaseStatList(): List<BaseStat> {
    return listOf(
        BaseStat(
            type = stringResource(id = R.string.hp),
            progress = hp / PokemonInfo.MAX_BASE_STAT.toFloat(),
            color = HpBg,
            label = getHpString()
        ),
        BaseStat(
            type = stringResource(id = R.string.attack),
            progress = attack / PokemonInfo.MAX_BASE_STAT.toFloat(),
            color = AttackBg,
            label = getAttackString()
        ),
        BaseStat(
            type = stringResource(id = R.string.defense),
            progress = defense / PokemonInfo.MAX_BASE_STAT.toFloat(),
            color = DefenseBg,
            label = getDefenseString()
        ),
        BaseStat(
            type = stringResource(id = R.string.special_attack),
            progress = specialAttack / PokemonInfo.MAX_BASE_STAT.toFloat(),
            color = SpecialAttackBg,
            label = getSpecialAttackString()
        ),
        BaseStat(
            type = stringResource(id = R.string.special_defense),
            progress = specialDefense / PokemonInfo.MAX_BASE_STAT.toFloat(),
            color = SpecialDefenseBg,
            label = getSpecialDefenseString()
        ),
        BaseStat(
            type = stringResource(id = R.string.speed),
            progress = speed / PokemonInfo.MAX_BASE_STAT.toFloat(),
            color = SpeedBg,
            label = getSpeedString()
        )
    )
}