package com.example.mvpsample.contract

interface MainContract
{
    interface View
    {
        fun showMyToast(nCount : Int)
    }

    interface Presenter
    {
        fun saveClick()
    }
}