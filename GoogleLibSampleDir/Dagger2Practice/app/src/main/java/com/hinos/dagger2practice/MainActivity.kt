package com.hinos.dagger2practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.hinos.dagger2practice.data.ComputerParts
import com.hinos.dagger2practice.data.book.DaggerNoteBookComponent
import com.hinos.dagger2practice.data.book.GallaxyBook
import com.hinos.dagger2practice.data.book.NoteBook
import com.hinos.dagger2practice.data.book.NoteBookComponent
import com.hinos.dagger2practice.databinding.ActivityMainBinding
import com.hinos.dagger2practice.di.BookModule
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity()
{
    private lateinit var mBinding : ActivityMainBinding

    @Inject
    @Named("MacBook")
    lateinit var mMackBook : NoteBook

    @Inject
    @Named("GallaxyBook")
    lateinit var mGallaxyBook : NoteBook

    lateinit var bookComponent : NoteBookComponent

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        bookComponent = DaggerNoteBookComponent.builder()
            .bookModule(
                BookModule(
                    ComputerParts(
                        cpu = "3700x",
                        ramSize = "16GB",
                        diskSize = "1TB"
                    ), "Samsung")
            )
            .build()

        val gallaxyNotebook : NoteBook = bookComponent.getBook()
        mBinding.noteBook = gallaxyNotebook

    }

    fun onBtnInsert(view: View)
    {
        mBinding.run {
            val cpu = etCPU.text.toString()
            val ram = etRAM.text.toString()
            val company = etCOMPANY.text.toString()
            val disk = etDISK.text.toString()

            bookComponent = DaggerNoteBookComponent.builder()
                .bookModule(
                    BookModule(
                        ComputerParts(
                            cpu = cpu,
                            ramSize = ram,
                            diskSize = disk
                        ), company)
                )
                .build()

            val noteBook : NoteBook = bookComponent.getBook()
            mBinding.noteBook = noteBook
        }
    }
    fun onBtnDefaultMac(view: View)
    {
        bookComponent.injectDefaultNoteBook(this)
        mBinding.noteBook = mMackBook
    }
    fun onBtnDefaultGallaxy(view: View)
    {
        bookComponent.injectDefaultNoteBook(this)
        mBinding.noteBook = mGallaxyBook
    }
}