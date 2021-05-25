package com.example.coroutinesample.basics

import kotlinx.coroutines.*

/**
 * 코루틴은 일시 중단 가능한 계산의 인스턴스입니다.
 * 코드의 나머지 부분과 동시에 동작하는 코드 블록이 실행된다는 점에서, 개념적으로 스레드(thread)와 유사하다.
 * 그러나 코루틴은 특정 스레드에 바인딩되지 않습니다. 한 스레드에서 실행을 일시 중단하고 다른 스레드에서 다시 시작할 수 있습니다.
 * 코루틴은 그저 경략 쓰레드라 생각할 수 있지만 실생활의 용도를 쓰레드와 매우 다르게 만드는 여러 가지 중요한 차이점이 있다.
 * */

private val scope = CoroutineScope(Dispatchers.IO)

fun main() = runBlocking { //테스트용으로만 runBlocking 사용, 쓰레드 전체를 중지시키기 때문에 ANR이 발생할 수 있음
    runBlocking {
        onClickButton()
        delay(1 * 1000)
        scope.cancel()
        delay(2 * 1000)
    }
}

fun onClickButton()
{
    scope.launch {
        downloadPicture()
    }
}

suspend fun downloadPicture() = coroutineScope {
    var i = 0
    while (isActive && i < 500000)
    {
        println(i)
        i++
    }
    if (!isActive)
    {
        println("Download Canceled")
    }
    if (i >= 500000)
    {
        println("Download Completed")
    } else {
        println("Download fail")
    }
}
