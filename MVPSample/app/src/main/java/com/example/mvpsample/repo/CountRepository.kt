package com.example.mvpsample.repo

import com.example.mvpsample.data.ClickVo

interface CountRepository
{
    suspend fun saveClick()
    suspend fun getClickCount() : Int
}