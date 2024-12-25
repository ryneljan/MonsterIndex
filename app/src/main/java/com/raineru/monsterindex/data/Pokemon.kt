package com.raineru.monsterindex.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(

    @SerialName(value = "name")
    val nameField: String
) {

    val name: String
        get() = nameField.replaceFirstChar { it.uppercase() }
}
