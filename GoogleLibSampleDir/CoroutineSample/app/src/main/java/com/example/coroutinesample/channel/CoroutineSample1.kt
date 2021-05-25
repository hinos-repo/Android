package com.example.coroutinesample.channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main()
{
    runBlocking {
        val channel = Channel<Int>()
        launch {
            for (x in 1..5) {
                println("send")
                channel.send(x * x)
            }
        }
        repeat(5) {
            val v = channel.receive()
            println("receive")
            println("$v")
        }
    }
}