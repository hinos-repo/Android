package com.example.mvvmsample

import com.example.mvvmsample.data.AppDatabase
import com.example.mvvmsample.data.ClickVo
import com.example.mvvmsample.view.MainActivity
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val c = Class.forName("com.example.mvvmsample.view.MainActivity")
        val vo = c.newInstance() as MainActivity
        print(vo)

        var str1 = "ABCD"
        var str2 = "ABCD"
        println(str1 == str2)
        println(str2.hashCode())

        str1 = "ABCDE"
        str2 = "ABCDEF"

        println(str1 == str2)
        println(str2.hashCode())
    }
}
