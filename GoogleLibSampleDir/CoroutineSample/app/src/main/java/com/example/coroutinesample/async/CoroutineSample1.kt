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

//suspend fun getUser(userId: String): String =
//    coroutineScope { // 이 suspend function을 실행할 때 사용된 스코프를 사용할 수 있게 해줍니다.
//        val deferred = async(Dispatchers.IO) {
//            userService.getUser(userId)
//        }
//        deferred.await()
//    }
