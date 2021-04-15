package com.yklib.livedatasample.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yklib.livedatasample.repository.InputMsgRepository
import com.yklib.livedatasample.viewmodel.MainViewModel

class MainViewModelFactory(
    private val inputMsgRepository: InputMsgRepository
) : ViewModelProvider.Factory
{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T
    {
        return MainViewModel(inputMsgRepository) as T
    }
}