package com.example.unittestsample

import java.util.*
import kotlin.collections.ArrayList

class MyDataList : ArrayList<MyDataList.MyData>()
{
    fun sortByDesc()
    {
        Collections.sort(this, MyComparator(true))
    }

    fun sortByAsc()
    {
        Collections.sort(this, MyComparator(false))
    }

    inner class MyComparator(val desc : Boolean) : Comparator<MyData>
    {
        override fun compare(lhs: MyData, rhs: MyData): Int
        {
            return compareInt(lhs.id, rhs.id)
        }

        fun compareInt(a1: Int, a2: Int): Int
        {
            if (desc)
            {
                if (a1 > a2)
                    return 1
            }
            else
            {
                if (a1 < a2)
                    return 1
            }
            return if (a1 == a2) 0 else -1
        }
    }

    data class MyData(
        val id : Int,
        val name : String
    )
}