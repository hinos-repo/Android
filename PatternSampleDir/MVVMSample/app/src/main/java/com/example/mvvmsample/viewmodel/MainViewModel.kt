package com.example.mvvmsample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmsample.repo.CountRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainViewModel(
    private val m_repo: CountRepository
) : ViewModel()
{

    var m_nCount : MutableLiveData<Int> = MutableLiveData()

    fun saveClick()
    {
        GlobalScope.launch {
            m_repo.saveClick()
            m_nCount.postValue(m_repo.getClickCount())
        }
    }
}