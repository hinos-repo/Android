package com.example.coroutinesample.async

import kotlinx.coroutines.*


private val m_scope = CoroutineScope(Dispatchers.IO)
fun main() {
    m_scope.launch {
        launch {
            println("1")
        }
        launch {
            println("2")
        }
        val string = sum("Hello", "World")
        println(string)
    }
    Thread.sleep(1 * 1000)
}

suspend fun sum(s1 : String, s2 : String) : String =
    coroutineScope {
        val defer1 = async(Dispatchers.IO, CoroutineStart.LAZY) {
            "$s1 $s2 ★ "
        }
        val defer2 = async(Dispatchers.IO, CoroutineStart.DEFAULT) {
            "$s1 $s2 "
        }
        val defer3 = async(Dispatchers.IO, CoroutineStart.DEFAULT) {
            "$s1 $s2 ※ "
        }
        defer1.await() + defer2.await()
    }
