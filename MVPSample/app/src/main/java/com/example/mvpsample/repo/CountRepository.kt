package com.example.mvpsample.repo


interface CountRepository
{
    suspend fun saveClick()
    suspend fun getClickCount() : Int
}