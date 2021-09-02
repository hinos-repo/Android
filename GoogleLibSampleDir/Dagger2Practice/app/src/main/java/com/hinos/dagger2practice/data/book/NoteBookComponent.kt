package com.hinos.dagger2practice.data.book

import com.hinos.dagger2practice.MainActivity
import com.hinos.dagger2practice.di.BookModule
import dagger.Component

@Component(modules = [BookModule::class])
interface NoteBookComponent
{
//    fun injectBook(gallaxyBook: GallaxyBook)
    fun getBook() : NoteBook
    fun injectDefaultNoteBook(mainActivity: MainActivity)
}