package com.example.dagger2sample.module

import com.example.dagger2sample.dao.Dog
import dagger.Module
import dagger.Provides

@Module
object DogModule
{
    @Provides
    fun provideDog() : Dog
    {
        return Dog()
    }
}