package com.example.mvvmsample.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmsample.repo.CountRepository
import com.example.mvvmsample.viewmodel.MainViewModel

class MainViewModelFactory(
    private val m_repo: CountRepository
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return MainViewModel(m_repo) as T
    }
}