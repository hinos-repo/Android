package com.hinos.dagger2practice.data.book

import com.hinos.dagger2practice.data.ComputerParts
import javax.inject.Inject

class MacBook @Inject constructor (private val parts : ComputerParts, private val fromCompany : String) : NoteBook
{
    override fun getBookFromCompany(): String
    {
        return fromCompany
    }

    override fun getCPUName(): String
    {
        return parts.cpu
    }

    override fun getRamSize(): String
    {
        return parts.ramSize
    }

    override fun getDiskSize(): String
    {
        return parts.diskSize
    }
}