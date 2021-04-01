package com.example.mvvmsample.repo

import androidx.lifecycle.LiveData
import com.example.mvvmsample.data.ClickVo

interface CountRepository
{
    suspend fun saveClick()
    suspend fun getClickCount() : Int
}