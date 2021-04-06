package com.example.unittestsample

import android.content.Context
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleMainActivityTest
{
    private lateinit var m_context : Context

    @Before
    fun useAppContext()
    {
        m_context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun isCheckTextViewCount()
    {
        ActivityScenario.launch(MainActivity::class.java).onActivity {
            val nSize = 50
            val arrTv = it.createTextViewList(m_context, nSize)
            assertEquals(nSize, arrTv.size-1)
        }
    }
}