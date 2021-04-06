package com.example.unittestsample

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleMyDataTest
{
    private val m_arrData = MyDataList()
    private val m_nSize = 30
    @Before
    fun initData()
    {
        for (i in 0..m_nSize)
        {
            m_arrData.add(MyDataList.MyData(i, "$i is My Name"))
        }
    }

    @Test
    fun sort_my_data_list_desc_test()
    {
        m_arrData.shuffle()
        m_arrData.sortByDesc()

        assertEquals(0, m_arrData[0].id)
    }

    @Test
    fun sort_my_data_list_asc_test()
    {
        m_arrData.shuffle()
        m_arrData.sortByAsc()

        assertEquals(m_nSize, m_arrData[0].id)
    }
}