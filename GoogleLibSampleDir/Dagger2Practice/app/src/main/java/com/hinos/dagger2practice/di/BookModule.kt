package com.hinos.dagger2practice.di

import com.hinos.dagger2practice.data.ComputerParts
import com.hinos.dagger2practice.data.book.GallaxyBook
import com.hinos.dagger2practice.data.book.MacBook
import com.hinos.dagger2practice.data.book.NoteBook
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named

@Module
class BookModule @Inject constructor(private var parts : ComputerParts, private var fromCompany : String)
{
    @Provides
    fun providesFromCompany() : String
    {
        return fromCompany
    }

    @Provides
    fun providesParts() : ComputerParts
    {
        return parts
    }

    @Provides
    fun providesNoteBook() : NoteBook
    {
        if (fromCompany == "Samsung")
        {
            return GallaxyBook(parts, fromCompany)
        }
        return MacBook(parts, fromCompany)
    }

    @Provides
    @Named("GallaxyBook")
    fun providesGallaxyBook() : NoteBook
    {
        return GallaxyBook(
            defaultGallaxyBookParts(), "Samsung")
    }

    @Provides
    @Named("MacBook")
    fun providesMacBook() : NoteBook
    {
        return MacBook(
            defaultMacBookParts(), "Apple")
    }

    private fun defaultGallaxyBookParts() : ComputerParts
    {
        return ComputerParts(
            cpu = "3700x",
            ramSize = "16GB",
            diskSize = "1TB"
        )
    }

    private fun defaultMacBookParts() : ComputerParts
    {
        return ComputerParts(
            cpu = "i7-6770",
            ramSize = "32GB",
            diskSize = "1TB"
        )
    }
}