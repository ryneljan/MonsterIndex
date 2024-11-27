package com.raineru.monsterindex

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json

class Greeting {
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun getGreeting(): String {
        val response = client.get("https://pokeapi.co/api/v2/pokemon/gengar")
        return response.bodyAsText()
    }
}