package com.example.facotrymethod.factory

import com.example.facotrymethod.template.Americano
import com.example.facotrymethod.template.Coffee
import com.example.facotrymethod.template.Latte
import java.lang.IllegalArgumentException

class CoffeeFactory
{
    fun createCoffee(className : String) : Coffee
    {
        return when(className)
        {
            Latte::class.java.simpleName ->
            {
                return Latte(
                    Latte::class.java.simpleName
                )
            }
            Americano::class.java.simpleName ->
            {
                return Americano(
                    Americano::class.java.simpleName
                )
            }
            else -> throw IllegalArgumentException("생성 할 수 없는 객체명입니다.")
        }
    }
}