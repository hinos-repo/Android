package com.example.dagger2sample.module

import com.example.dagger2sample.dao.Cat
import dagger.Module
import dagger.Provides

@Module
object CatModule
{
    @Provides
    fun provideCat() : Cat
    {
        return Cat()
    }
}