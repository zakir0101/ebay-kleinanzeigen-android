package com.example.ebaykleinanzeigenzakir.data

import android.content.SharedPreferences
import com.example.ebaykleinanzeigenzakir.network.AddCookiesInterceptor
import com.example.ebaykleinanzeigenzakir.network.EbayService
import com.example.ebaykleinanzeigenzakir.network.ReceivedCookiesInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



interface EbayContainer {
    val ebayRepository : EbayRepository
}


class DefaultEbayContainer(sharedPref: SharedPreferences) :EbayContainer{

    private  val BASE_URL = "https://ebay-zakir-1996.onrender.com/"


    private val okHttpClient = OkHttpClient().newBuilder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .addInterceptor(AddCookiesInterceptor(sharedPref))
        .addInterceptor(ReceivedCookiesInterceptor(sharedPref))


    private val retrofit = Retrofit.Builder().client(okHttpClient.build())
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()


    val ebayService: EbayService by lazy {
        retrofit.create(EbayService::class.java)
    }


    override val ebayRepository: EbayRepository by lazy { NetworkEbayRepository(ebayService,sharedPref) }
}