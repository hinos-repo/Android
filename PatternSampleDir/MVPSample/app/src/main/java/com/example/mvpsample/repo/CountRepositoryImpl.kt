package com.example.mvpsample.repo

import com.example.mvpsample.data.AppDatabase
import com.example.mvpsample.data.ClickVo

class CountRepositoryImpl(
    private val m_database : AppDatabase
) : CountRepository
{

    override suspend fun saveClick()
    {
        m_database.daoClick().insert(ClickVo())
    }

    override suspend fun getClickCount() : Int
    {
        return m_database.daoClick().getCount()
    }
}