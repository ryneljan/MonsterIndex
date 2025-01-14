package com.raineru.monsterindex

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class EmitTryEmitTest {

    suspend fun fetchData(): String {
        delay(1000L)
        return "Hello world"
    }

    @Test
    fun dataShouldBeHelloWorld() = runTest {
        var name: String? = null
        val emitter:MutableStateFlow<String?> = MutableStateFlow(null)

        val successful = emitter.tryEmit("Bulbasaur")

        assertEquals(true, successful)
    }
}