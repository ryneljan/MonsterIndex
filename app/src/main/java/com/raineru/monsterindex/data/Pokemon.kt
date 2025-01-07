package com.raineru.monsterindex.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    var page: Int = 0,

    @SerialName(value = "name")
    val nameField: String,

    @SerialName(value = "url")
    val url: String
) {

    val name: String
        get() = nameField.replaceFirstChar { it.uppercase() }
}
