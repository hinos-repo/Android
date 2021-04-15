package com.mysample.inapptestproject.retrofit

import com.mysample.inapptestproject.common.MyConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetRetrofit
{
    val retrofit = Retrofit.Builder().baseUrl(MyConfig.MYURL).addConverterFactory(GsonConverterFactory.create()).build()
    val service: RetrofitService = retrofit.create(RetrofitService::class.java)
    val instance = NetRetrofit
}
