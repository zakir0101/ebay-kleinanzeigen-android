package com.example.ebaykleinanzeigenzakir.network

import android.content.SharedPreferences
import android.util.Log
import com.example.ebaykleinanzeigenzakir.Cookie
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.net.HttpCookie


private const val ClassName = "Interceptor"

class AddCookiesInterceptor(private val preferenceProvider: SharedPreferences) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        //************ reading and parsing json Cookies
        val prefCookies: String? =
            preferenceProvider.getString("PREF_COOKIES", "[]")
        var cookieList = Gson().fromJson(prefCookies, Array<Cookie>::class.java).asList()

        //************* filtering expired cookies *********************
        val currentTimeSec = System.currentTimeMillis()  / 1000
        cookieList = cookieList.filter { it.expirationDate > currentTimeSec }


        //************* prepering cookie for request *******************
        val requestCookies = StringBuilder()
        cookieList.forEachIndexed { index, cookie ->
            val cook = HttpCookie(cookie.name, cookie.value)
            if (index > 0)
                requestCookies.append("; ")
            requestCookies.append(cook.toString())
        }
        builder.addHeader("Cookie", requestCookies.toString())

        //*************** Saving Cookie after filter *********************
        val cookieString = Gson().toJson(cookieList)
        with(preferenceProvider.edit()) {
            putString("PREF_COOKIES", cookieString)
            apply()
        }


        return chain.proceed(builder.build())
    }
}


class ReceivedCookiesInterceptor(private val preferenceProvider: SharedPreferences) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty()) {

            //************ reading and parsing pref Cookies ****************
            val prefCookiesString: String? =
                preferenceProvider.getString("PREF_COOKIES", "[]")
            var prefCookies = Gson().fromJson(prefCookiesString, Array<Cookie>::class.java).toHashSet()



            //************ reading request Cookies and adding to pref
            val responseCookies = mutableListOf<Cookie>()
            for (header in originalResponse.headers("Set-Cookie")) {
                val cook: HttpCookie = HttpCookie.parse(header)[0]
                val expirationTime = cook.maxAge.toDouble()+(System.currentTimeMillis()/1000 )
                val cookie: Cookie = Cookie(
                    cook.domain, expirationTime, false, cook.isHttpOnly,
                    cook.name,cook.value ,cook.path,"unspecified",cook.secure,
                    false,""
                )
                Log.d(ClassName,"Recieved Cookies "+cook.name +" "+cook.maxAge)

                if (prefCookies.contains(cookie))
                    prefCookies.remove(cookie)
                prefCookies.add(cookie)
            }

            //********************* saving cookies **********************
            val cookieString = Gson().toJson(prefCookies)
            with(preferenceProvider.edit()) {
                putString("PREF_COOKIES", cookieString)
                apply()
            }
            // Prefrence Provider In My SharedPrefrence Object
        }
        return originalResponse
    }
}