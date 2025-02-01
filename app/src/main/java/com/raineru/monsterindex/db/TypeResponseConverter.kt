package com.raineru.monsterindex.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.raineru.monsterindex.model.PokemonInfo.TypeResponse
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@ProvidedTypeConverter
class TypeResponseConverter @Inject constructor(
    private val json: Json
) {

    @TypeConverter
    fun fromString(value: String): List<TypeResponse> {
        return json.decodeFromString(value)
    }

    @TypeConverter
    fun fromTypeResponse(value: List<TypeResponse>): String {
        return json.encodeToString(value)
    }
}