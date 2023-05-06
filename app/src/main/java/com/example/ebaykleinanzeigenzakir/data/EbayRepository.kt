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
    suspend fun getSearchResults(
        city: City,
        category: Category,
        filterData: FilterData
    ): SearchWindowData?

    suspend fun getConversationData(userId: String,page:String,size: String): ConversationWindowData?
    suspend fun getMessages(userId: String, conversationId: String): Conversation?
    suspend fun sendMessageFromMessageBox(
        message: String,
        userId: String,
        conversationId: String
    ): SendMessageResponse?

    suspend fun publishAdd(formData: PublishFormData, city: City): PublishAddResponse?
    suspend fun checkAdd(addId: String): PublishAddResponse?
    suspend fun getSettingData(): SettingWindowData?
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


    override suspend fun getUserData(userLink: String): UserWindowData? {
        val response = ebayService.getUser(userLink)
        return extractBodyFromResponse<UserWindowData>("getUserData", response)
    }


    override suspend fun getCities(cityName: String): List<City>? {
        val response = ebayService.getCities(cityName)
        return extractBodyFromResponse<List<City>>("getCities", response)
    }

    override suspend fun getCategories(): List<Category>? {
        val response = ebayService.getCategories()
        return extractBodyFromResponse<List<Category>>("getCategories", response)
    }

    override suspend fun getSearchResults(
        city: City,
        category: Category,
        filterData: FilterData
    ): SearchWindowData? {
        var searchToken = "";
        var path = ""
        if (!filterData.search.isBlank())
            searchToken += "k0"
        else
            filterData.search = "None"
        if (category.code != allCategories.code)
            searchToken += category.code
        if (city.code != deutschland.code) {
            searchToken += "l" + city.code
            if (filterData.activeRange.isNotBlank())
                searchToken += "r" + filterData.activeRange
        }
        if (searchToken.isBlank())
            searchToken = "None"
        if (filterData.anbieter.isNotBlank())
            path += "anbieter:" + filterData.anbieter + "/"
        if (filterData.anzeige.isNotBlank())
            path += "anzeige:" + filterData.anzeige + "/"
        if (filterData.direktKaufen.isNotBlank())
            path += "direktkaufen:" + filterData.direktKaufen + "/";
        if (filterData.paketdient.isNotBlank())
            path += "paketdienst:" + filterData.paketdient + "/";
        if (filterData.priceFrom.isNotBlank() || filterData.priceTo.isNotBlank())
            path += "preis:" + filterData.priceFrom + ":" + filterData.priceTo + "/";
        if (path.isBlank())
            path = "None"

        val response = ebayService.getSearchResults(filterData.search, searchToken, path)
        return extractBodyFromResponse<SearchWindowData>("getSearchResults", response)
    }

    override suspend fun getConversationData(userId: String,page:String,size:String): ConversationWindowData? {
        val response = ebayService.getConversation(userId,page,size)
        return extractBodyFromResponse<ConversationWindowData>("getConversation", response)
    }

    override suspend fun getMessages(userId: String, conversationId: String): Conversation? {
        val response = ebayService.getMessages(userId, conversationId)
        return extractBodyFromResponse<Conversation>("getMessages", response)
    }

    override suspend fun sendMessageFromMessageBox(
        message: String,
        userId: String,
        conversationId: String
    ): SendMessageResponse? {
        val response = ebayService.sendMessageFromMessageBox(message, userId, conversationId)
        return extractBodyFromResponse<SendMessageResponse>("sendMessageFromMessageBox", response)
    }

    override suspend fun publishAdd(formData: PublishFormData, city: City): PublishAddResponse? {
        val response = ebayService.publishAdd(
            formData.title,
            formData.price,
            city.zip,
            city.code,
            formData.description,
            formData.contact_name
        )
        return extractBodyFromResponse<PublishAddResponse>("publishAdd", response)
    }

    override suspend fun checkAdd(addId:String): PublishAddResponse? {
        val response = ebayService.checkAdd(addId)
        return extractBodyFromResponse<PublishAddResponse>("checkAdd", response)
    }


    override suspend fun getSettingData(): SettingWindowData? {
        val response = ebayService.getSetting()
        return extractBodyFromResponse<SettingWindowData>("getSetting", response)
    }


}