package com.example.dagger2sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.dagger2sample.component.DaggerPetComponent
import com.example.dagger2sample.dao.Cat
import com.example.dagger2sample.dao.Dog
import com.example.dagger2sample.databinding.ActivityMainBinding
import com.example.dagger2sample.module.CatModule
import com.example.dagger2sample.module.DogModule
import javax.inject.Inject

class MainActivity : AppCompatActivity()
{
    private lateinit var m_binding : ActivityMainBinding

    @Inject
    lateinit var m_cat : Cat

    @Inject
    lateinit var m_dog : Dog

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        injectComponent()
        m_binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        m_binding.run {
            mainActivitiy = this@MainActivity
            cat = m_cat
            dog = m_dog
        }
    }

    private fun injectComponent()
    {
        DaggerPetComponent.builder()
            .catModule(CatModule)
            .dogModule(DogModule)
            .build()
            .inject(this)
    }

    fun showMyToast(strMessage : String)
    {
        Toast.makeText(this, strMessage, Toast.LENGTH_SHORT).show()
    }

}