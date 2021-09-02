package com.hinos.dagger2practice

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.hinos.dagger2practice.data.ComputerParts
import com.hinos.dagger2practice.data.book.DaggerNoteBookComponent
import com.hinos.dagger2practice.data.book.NoteBook
import com.hinos.dagger2practice.di.BookModule

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest
{
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.hinos.dagger2practice", appContext.packageName)
    }

    @Test
    fun test()
    {
        val gallaxyNotebook : NoteBook = DaggerNoteBookComponent.builder()
            .bookModule(
                BookModule(
                    ComputerParts(
                    cpu = "3700x",
                    ramSize = "16GB",
                    diskSize = "1TB"
                ), "Samsung")
            )
            .build()
            .getBook()

        gallaxyNotebook.run {
            println(getCPUName())
            println(getBookFromCompany())
            println(getDiskSize())
            println(getRamSize())
        }

        val appleNoteBook : NoteBook = DaggerNoteBookComponent.builder()
            .bookModule(
                BookModule(
                    ComputerParts(
                        cpu = "i7-6770",
                        ramSize = "32GB",
                        diskSize = "1TB"
                    ), "Apple")
            )
            .build()
            .getBook()

        appleNoteBook.run {
            println(getCPUName())
            println(getBookFromCompany())
            println(getDiskSize())
            println(getRamSize())
        }


        Thread.sleep(20 * 1000)
    }
}
