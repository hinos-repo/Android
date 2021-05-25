package com.example.coroutinesample.basics

import kotlinx.coroutines.*

fun main() = runBlocking {
    val v = withContext(Dispatchers.IO) {
        var total = 0
        for (i in 1..10)
        {
            delay(100)
            total+=i
        }
        return@withContext total
    }
    println("$v")
}

