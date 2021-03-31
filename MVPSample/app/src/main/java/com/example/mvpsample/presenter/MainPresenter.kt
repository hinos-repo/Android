package com.example.mvpsample.presenter

import com.example.mvpsample.contract.MainContract
import com.example.mvpsample.repo.CountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainPresenter(
    private val m_view: MainContract.View,
    private val m_repo: CountRepository
) : MainContract.Presenter {

    override fun saveClick()
    {
        GlobalScope.launch {
            m_repo.saveClick()
            val nCount = m_repo.getClickCount()
            withContext(Dispatchers.Main) {
                m_view.showMyToast(nCount)
            }
        }
    }
}