package com.example.facotrymethod

import com.example.facotrymethod.factory.CoffeeFactory
import com.example.facotrymethod.template.Americano
import com.example.facotrymethod.template.Latte
import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun check_coffee_factory()
    {
        val factory = CoffeeFactory()

        val coffee1 = factory.createCoffee(Latte::class.java.simpleName)
        println("1. ${coffee1.getCoffeeName()}")
        println(coffee1.getCoffeeDetailInfo())


        println()
        println()


        val coffee2 = factory.createCoffee(Americano::class.java.simpleName)
        println("2. ${coffee2.getCoffeeName()}")
        println(coffee2.getCoffeeDetailInfo())

        Assert.assertEquals(false, coffee1.getCoffeeName() == coffee2.getCoffeeName())
        Assert.assertEquals(false, coffee1.getCoffeeDetailInfo() == coffee2.getCoffeeDetailInfo())
    }
}