package com.raineru.monsterindex.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.raineru.monsterindex.model.PokemonInfo.StatsResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@ProvidedTypeConverter
class StatsResponseConverter @Inject constructor(
    private val json: Json
) {

    @TypeConverter
    fun fromString(value: String): List<StatsResponse> {
        return json.decodeFromString(value)
    }

    @TypeConverter
    fun fromStatsResponse(value: List<StatsResponse>): String {
        return json.encodeToString(value)
    }
}