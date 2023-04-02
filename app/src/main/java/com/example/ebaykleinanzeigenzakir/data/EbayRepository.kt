package com.example.ebaykleinanzeigenzakir.data

import android.content.SharedPreferences
import android.util.Log
import com.example.ebaykleinanzeigenzakir.*
import com.example.ebaykleinanzeigenzakir.network.EbayService
import retrofit2.Response

private const val ClassName = "EBAY_REPOSITORY"

interface EbayRepository {

    suspend fun getSharedPreference(): SharedPreferences
    suspend fun saveCookies(cookiesString: String): Unit

    suspend fun isUserLogged(): LoginData?
    suspend fun logout()
    suspend fun getMainData(): MainWindowData?
    suspend fun getAddData(addLink: String): AddWindowData?
    suspend fun sendMessageFromAddPage(
        message: String,
        contactName: String,
        activeAdd: AddWindowData
    ): SendMessageResponse?

    suspend fun getUserData(userLink: String): UserWindowData?
    suspend fun getCities(cityName: String): List<City>?
    suspend fun getCategories(): List<Category>?
}


class NetworkEbayRepository(
    private val ebayService: EbayService,
    val sharedPref: SharedPreferences
) : EbayRepository {


    override suspend fun saveCookies(cookiesString: String) {
        with(sharedPref.edit()) {
            putString("PREF_COOKIES", cookiesString)
            apply()
        }
    }

    override suspend fun getSharedPreference(): SharedPreferences {
        return sharedPref
    }

    private suspend fun <T> extractBodyFromResponse(tagName: String, response: Response<T>): T? {
        when (response.isSuccessful) {
            true -> {
                return response.body()
            }
            false -> {
                Log.e("RETROFIT_ERROR $ClassName $tagName", response.code().toString())
                Log.e("RETROFIT_ERROR $ClassName $tagName", response.errorBody().toString())
                return null
            }
        }
    }

    override suspend fun isUserLogged(): LoginData? {
        val response = ebayService.isUserLogged()
        return extractBodyFromResponse<LoginData>("isUserLogged", response)
    }


    override suspend fun logout() {
        with(sharedPref.edit()) {
            putString("PREF_COOKIES", "[]")
            apply()
        }
    }


    override suspend fun getMainData(): MainWindowData? {
        val response = ebayService.getMain()
        return extractBodyFromResponse("getMain", response)
    }


    override suspend fun getAddData(addLink: String): AddWindowData? {
        val response = ebayService.getAdd(addLink)
        return extractBodyFromResponse<AddWindowData>("getAdd", response)
    }

    override suspend fun sendMessageFromAddPage(
        message: String,
        contactName: String,
        activeAdd: AddWindowData
    ): SendMessageResponse? {

        val addType = when ("Privater") {
            in activeAdd.user.user_type -> "privat"
            else -> "gewerblich"
        }
        val response = ebayService.sendMessageFromAddPage(
            message, activeAdd.add_id,
            addType, contactName
        )
        return extractBodyFromResponse<SendMessageResponse>("sendMessageFromAddPage", response)
    }


    override suspend fun getUserData(userLink:String): UserWindowData? {
        val response = ebayService.getUser(userLink)
        return extractBodyFromResponse<UserWindowData>("getUserData", response)
    }


    override suspend fun getCities(cityName:String): List<City>? {
        val response = ebayService.getCities(cityName)
        return extractBodyFromResponse<List<City>>("getCities", response)
    }

    override suspend fun getCategories(): List<Category>? {
        val response = ebayService.getCategories()
        return extractBodyFromResponse<List<Category>>("getCategories", response)
    }

    override suspend fun getSearchResults(city:City,category: Category,filterData: FilterData): SearchWindowData? {



        val response = ebayService.getSearchResults()
        return extractBodyFromResponse<SearchWindowData>("getSearchResults", response)
    }

    override suspend fun getConversationData(): ConversationWindowData? {
        val response = ebayService.getConversation()
        return extractBodyFromResponse<ConversationWindowData>("getConversation", response)
    }

    override suspend fun getMessages(): Conversation? {
        val response = ebayService.getMessages()
        return extractBodyFromResponse<Conversation>("getMessages", response)
    }

    override suspend fun sendMessageFromMessageBox(): SendMessageResponse? {
        val response = ebayService.sendMessageFromMessageBox()
        return extractBodyFromResponse<SendMessageResponse>("sendMessageFromMessageBox", response)
    }

    override suspend fun publishAdd(): PublishAddResponse? {
        val response = ebayService.publishAdd()
        return extractBodyFromResponse<PublishAddResponse>("publishAdd", response)
    }

    override suspend fun checkAdd(): PublishAddResponse? {
        val response = ebayService.checkAdd()
        return extractBodyFromResponse<PublishAddResponse>("checkAdd", response)
    }


    override suspend fun getSettingData(): SettingWindowData? {
        val response = ebayService.getSetting()
        return extractBodyFromResponse<SettingWindowData>("getSetting", response)
    }


}