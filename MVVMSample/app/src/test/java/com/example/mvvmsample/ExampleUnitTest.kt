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
    }
}