package com.raineru.monsterindex.data

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object PokemonType : NavType<Pokemon>(
    isNullableAllowed = false
) {
    override fun put(bundle: Bundle, key: String, value: Pokemon) {
        bundle.putString(key, Json.encodeToString(value))
    }

    override fun get(bundle: Bundle, key: String): Pokemon? {
        return Json.decodeFromString(bundle.getString(key) ?: return null)
    }

    override fun parseValue(value: String): Pokemon {
        return Json.decodeFromString<Pokemon>(Uri.decode(value))
    }

    override fun serializeAsValue(value: Pokemon): String {
        return Uri.encode(Json.encodeToString(value))
    }
}