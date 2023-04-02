package com.example.ebaykleinanzeigenzakir.network

import android.util.Log
import com.example.ebaykleinanzeigenzakir.*
import kotlinx.coroutines.delay
import org.json.JSONObject

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface EbayService {
//    @Headers("Credentials: include")


    /******************************************
     *           Login
     ******************************************* */

    @GET("islogged")
    suspend fun isUserLogged(): Response<LoginData>

    /******************************************
     *           Main
     ******************************************* */

    @GET("main")
    suspend fun getMain(): Response<MainWindowData>


    /******************************************
     *           Add
     ******************************************* */

    @GET("/add/api")
    suspend fun getAdd(@Query("link") addLink: String): Response<AddWindowData>

    @GET("send/addpage")
    suspend fun sendMessageFromAddPage(
        @Query("message") message: String,
        @Query("add_id") addId: String,
        @Query("add_type") addType: String,
        @Query("contact_name") contactName: String
    ): Response<SendMessageResponse>

    /******************************************
     *           User
     ******************************************* */

    @GET("/user/api")
    suspend fun getUser(@Query("link") userLink: String): Response<UserWindowData>

    /******************************************
     *           Cities
     ******************************************* */


    @GET("cities/{city_name}")
    suspend fun getCities(@Path("city_name") cityName: String): Response<List<City>>

    /******************************************
     *           Categories
     ******************************************* */

    @GET("categories")
    suspend fun getCategories(): Response<List<Category>>

    /******************************************
     *           Search
     ******************************************* */

    @GET("search/{search_word}/{search_Token}/api")
    suspend fun getSearchResults(
        @Path("search_word") searchWord: String,
        @Path("search_token") searchToken: String,
        @Query("path") path: String
    ): Response<SearchWindowData>


    /******************************************
     *           Conversation
     ******************************************* */

    @GET("conversation")
    suspend fun getConversation(@Query("user_id") userId: String): Response<ConversationWindowData>


    /******************************************
     *           Message
     ******************************************* */

    @GET("messages/api")
    suspend fun getMessages(
        @Query("user_id") userId: String,
        @Query("conversation_id") conversationId: String
    ): Response<Conversation>


    @GET("send/messagebox")
    suspend fun sendMessageFromMessageBox(
        @Query("message") message: String,
        @Query("user_id") userId: String,
        @Query("conversation_id") conversationId: String
    ): Response<SendMessageResponse>


    /******************************************
     *           Publish
     ******************************************* */

    @GET("publish/api")
    suspend fun publishAdd(
        @Query("title") title: String, @Query("price") price: String,
        @Query("zip") zip: String, @Query("city_code") city_code: String,
        @Query("description") description: String, @Query("contact_name") contact_name: String
    ):Response<PublishAddResponse>


    @GET("check/add")
    suspend fun checkAdd(
        @Query("add_id") add_id: String, @Query("page") page: String,
        @Query("size") size: String
    ):Response<PublishAddResponse>


    /******************************************
     *           My Add
     ******************************************* */


    /******************************************
     *           Setting
     ******************************************* */

    @GET("setting/api")
    suspend fun getSetting(): Response<SettingWindowData>


}
