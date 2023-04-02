package com.example.ebaykleinanzeigenzakir

import android.app.Application
import android.content.Context
import com.example.ebaykleinanzeigenzakir.data.DefaultEbayContainer
import com.example.ebaykleinanzeigenzakir.data.EbayContainer

class EbayKleinanzeigenZakirApplication: Application() {
    lateinit var container: EbayContainer

    override fun onCreate() {
        super.onCreate()
        val sharedPref = getSharedPreferences(getString(R.string.ebay_preference),Context.MODE_PRIVATE)
//        val cookie = sharedPref.getString("PREF_COOKIES",null)
//        if (cookie == null) {
//            with(sharedPref.edit()) {
//                putString("PREF_COOKIES", "[]")
//                apply()
//            }
//        }
        container = DefaultEbayContainer(sharedPref)

    }

}