package com.example.coroutinesample.basics

import kotlinx.coroutines.*

fun main()  {
    println("start")
    GlobalScope.launch {
        withTimeout(4000L) {
            repeat(10) {
                delay(1000L)
                println("I'm working.")
            }
        }
    }
    Thread.sleep(5 * 1000)
    println("end")
}

