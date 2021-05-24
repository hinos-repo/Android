package com.example.coroutinesample.basics

import kotlinx.coroutines.*

private val m_job = Job()
private val m_scope = CoroutineScope(Dispatchers.IO + m_job)

fun main()
{
    m_scope.launch {
        val userName = fetchUser()
        show(userName)
    }

    m_scope.launch {
        val userName = fetchUser()
        show(userName)
    }


    var i = 0
    while (i < 5)
    {
        println("Main Thread Work $i....")
        i++
        Thread.sleep(1 * 1000)
//        m_scope.cancel()
    }
}

suspend fun fetchUser() : String
{
    var userName : String = ""
    coroutineScope { // HttpConnection
        delay(2 * 1000)
        userName = "Hinos"
    }
    return userName
}

fun show(userName : String)
{
    println(userName)
}
