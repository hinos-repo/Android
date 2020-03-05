package com.mysample.inapptestproject.retrofit

import com.google.gson.JsonObject
import com.mysample.inapptestproject.common.MyConfig
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url


interface RetrofitService
{
    @FormUrlEncoded
    @POST(MyConfig.SERVER_VERIFY)
    fun getBillingInfo(
            @Field("product_id")        product_id: String,
            @Field("purchase_token")    purchase_token: String,
            @Field("package_name")      package_name: String,
            @Field("mode")              mode: Int
    ): Call<JsonObject>

    @GET
    fun sendPromotion(@Url url: String): Call<JsonObject>
}