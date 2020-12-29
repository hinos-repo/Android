package com.yklib.livedatasample.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yklib.livedatasample.dao.AppDatabase
import com.yklib.livedatasample.databinding.ActivityMainBinding
import com.yklib.livedatasample.factory.MainViewModelFactory
import com.yklib.livedatasample.model.InputMsg
import com.yklib.livedatasample.repository.InputMsgRepository
import com.yklib.livedatasample.viewmodel.MainViewModel
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeUi()
    }

    private fun subscribeUi()
    {
        val dao = AppDatabase.getInstance(this).inputMsgDao()
        val repository = InputMsgRepository.getInstance(dao)


        val factory = MainViewModelFactory(repository)
        var viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewModel.inputMsgs.observe(this, Observer {
            if (it == null || it.isEmpty())
                return@Observer

            val sb = StringBuffer()
            for (data in it) {
                sb.append(data.msg).append("\n")
            }

            binding.tvResult.text = sb.toString()
        })

        binding.btnInput.setOnClickListener {
            val input = binding.etInput.text.toString()
            if (input.isEmpty())
                return@setOnClickListener

            binding.etInput.setText("")
            viewModel.insertMsg(InputMsg(msg = input))
        }
    }
}