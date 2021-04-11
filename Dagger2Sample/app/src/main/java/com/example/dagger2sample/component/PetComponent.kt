package com.example.dagger2sample.component

import com.example.dagger2sample.MainActivity
import com.example.dagger2sample.module.CatModule
import com.example.dagger2sample.module.DogModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DogModule::class, CatModule::class])
interface PetComponent
{
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface  Builder
    {
        fun build() : PetComponent

        fun dogModule(dogModule : DogModule) : Builder
        fun catModule(catModule : CatModule) : Builder
    }
}