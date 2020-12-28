package com.example.testproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testproject.databinding.ActivityMainBinding
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private val m_arrList = mutableListOf<SearchData>().apply {
        for (i in 1 .. 1000)
        {
            add(SearchData(i.toString()))
        }
    }
    private val m_compositDisposable = CompositeDisposable()
    private val m_adtSearchList : SearchListAdapter by lazy { SearchListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewSetting()
    }

    override fun onDestroy() {
        this.m_compositDisposable.clear()
        super.onDestroy()
    }

    private fun initViewSetting()
    {
        val editTextChangeObservable = binding.vEditText.textChanges()

        val searchEditTextSubscription : Disposable = editTextChangeObservable.debounce(800, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .subscribeBy (
                onNext = {
                    if (it.isEmpty()) return@subscribeBy
                    Handler(Looper.getMainLooper()).post {
                        m_adtSearchList.searchData(m_arrList, it.toString())
                    }
                    Log.d("RX", "onNext: $it")
                },
                onComplete = {
                    Log.d("RX", "onComplete")
                },
                onError = {
                    Log.d("RX", "onError : $it")
                }
            )
        m_compositDisposable.add(searchEditTextSubscription)

        binding.vRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.vRecyclerView.adapter = m_adtSearchList
    }
}  