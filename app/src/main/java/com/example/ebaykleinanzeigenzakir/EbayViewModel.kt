package com.example.ebaykleinanzeigenzakir

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ebaykleinanzeigenzakir.data.EbayRepository
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

private const val ClassName = "EbayViewModel"

class EbayViewModel(private val ebayRepository: EbayRepository) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val Application = (this[APPLICATION_KEY] as EbayKleinanzeigenZakirApplication)
                val Repo = Application.container.ebayRepository
                EbayViewModel(Repo)
            }
        }
    }

    init {
        isUserLogged()
    }

    /******************************************
     *              Application
     ******************************************* */

    var darkMode by mutableStateOf(false)
    var dynamicColor by mutableStateOf(false)
    var internetConencted by mutableStateOf(true)
    val switchDarkMode: () -> Unit = {
        darkMode = !darkMode
    }

    val switchDynamicColor: () -> Unit = {
        dynamicColor = !dynamicColor
    }

    private fun <T> checkNullability(funName:String,result:T?):Boolean{
        internetConencted = true

        if (result != null)
            Log.v(ClassName, "$funName() successfully " )
        else
            Log.e(ClassName, "$funName() result is null" )

        return result != null
    }

    private fun onIOException(funName:String,e:Exception){
        Log.e(ClassName, "netwrok error when calling $funName() :$e" )
        internetConencted = false

    }

    /******************************************
     *           Login Window
     ******************************************* */

    val loginState: MutableStateFlow<LoginData?> = MutableStateFlow(null)
    var cookieText by mutableStateOf("")
    var parsingStatus by mutableStateOf("")


    fun onCookieSubmit() {
        viewModelScope.launch {
            try {
                val cookieList = Gson().fromJson(cookieText, Array<Cookie>::class.java).asList()
                Log.d(ClassName, "first Cookie Value " + cookieList[0].value)
                parsingStatus = "Format accepted"

                try {
                    ebayRepository.saveCookies(cookieText)
                    val loginData = ebayRepository.isUserLogged()
                    if (loginData == null)
                        parsingStatus = "response is not valid"
                    else {
                        loginState.value = loginData
                        parsingStatus = when (loginData.is_logged) {
                            true -> "Login Successfully :)"
                            false -> "Could not Login :("
                        }
                    }
                    internetConencted = true
                    getMain()
                } catch (e: IOException) {
                    parsingStatus = "network error"
                    internetConencted = false
                }
            } catch (e: JsonSyntaxException) {
                parsingStatus = "Format not accepted"
            }
        }
    }


    fun isUserLogged() {
        val funName =  "isUserLogged"
        viewModelScope.launch {
            try {
                val result = ebayRepository.isUserLogged()
                loginState.value = result
                if(checkNullability(funName,result))
                    getMain()
            } catch (e: IOException) {
                onIOException(funName,e)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            loginState.value = null
            ebayRepository.logout()
            isUserLogged()
        }
    }

    /******************************************
     *           Main Window
     ******************************************* */

    val mainState = MutableStateFlow<MainWindowData?>(null)
    fun getMain() {
        val funName = "getMain"
        viewModelScope.launch {
            try {
                val result = ebayRepository.getMainData()
                mainState.value = result
                checkNullability(funName,result)
            }catch (e:IOException){
                onIOException(funName,e)
            }
        }
    }

    /******************************************
     *           Add Window
     ******************************************* */
    var activeAddLink  by mutableStateOf<String?>(null)
    val addWindowState = MutableStateFlow<AddWindowData?>(null)
    var addWindowContactName by mutableStateOf("")
    var addWindowMessage by mutableStateOf("")
    val addWindowSendMessageResponse = MutableStateFlow<SendMessageResponse?>(null)
    fun getAddData(){
        val funName = "getAddData"
        if (activeAddLink == null ){

            return
        }

        viewModelScope.launch {
            try {
                val result = ebayRepository.getAddData(activeAddLink!!)
                addWindowState.value = result
                checkNullability(funName,result)
            }catch (e:IOException){
                onIOException(funName,e)
            }
        }
    }
    fun sendMessageFromAddPage(){
        if (addWindowState.value == null)
            return
        val funName = "sendMessageFromAddPage"
        viewModelScope.launch {
            try {
                val result = ebayRepository.sendMessageFromAddPage(addWindowMessage,addWindowContactName,addWindowState.value!!)
                addWindowSendMessageResponse.value = result
                checkNullability(funName,result)
            }catch (e:IOException){
                onIOException(funName,e)
            }
        }
    }
    /******************************************
     *           User Window
     ******************************************* */
    val activeUserLink by mutableStateOf<String?>(null)
    val userWindowState = MutableStateFlow<UserWindowData?>(null)
    fun getUserData(){
        if (activeAddLink == null)
            return
        val funName = "getUserData"
        viewModelScope.launch {
            try {
                val result = ebayRepository.getUserData(activeUserLink!!)
                userWindowState.value = result
                checkNullability(funName,result)
            }catch (e:IOException){
                onIOException(funName,e)
            }
        }
    }

    /******************************************
     *           Search Window
     ******************************************* */
    val filterState  = MutableStateFlow<FilterData>(FilterData(""))
    fun getSearchResult(){

    }
    /******************************************
     *           Categories
     ******************************************* */
    val categories = MutableStateFlow<List<Category>?>(null)

    fun getCategories(){

        val funName = "getCategories"
        viewModelScope.launch {
            try {
                val result = ebayRepository.getCategories()
                categories.value = result
                checkNullability(funName,result)
            }catch (e:IOException){
                onIOException(funName,e)
            }
        }

    }

    /******************************************
     *           Cities
     ******************************************* */
    var citySearch by mutableStateOf("")
    val citiesList = MutableStateFlow<List<City>?>(null)

    fun getCities(){
        if (citySearch.isBlank() || citySearch.length < 2 )
            return
        val funName = "getCities"
        viewModelScope.launch {
            try {
                val result = ebayRepository.getCities(citySearch)
                citiesList.value = result
                checkNullability(funName,result)
            }catch (e:IOException){
                onIOException(funName,e)
            }
        }

    }
    /******************************************
     *           Conversation Window
     ******************************************* */
    val conversationState = MutableStateFlow<ConversationWindowData?>(null)
    var conversationSize by mutableStateOf(50)
    var conversationPage by mutableStateOf(0)

    fun getConversation(){

    }
    /******************************************
     *           Message Window
     ******************************************* */
    var messageWindowMessage by mutableStateOf("")
    var activeConversationId by mutableStateOf("")

    fun getMessages(){

    }

    fun sendMessageFromMessageBox(){

    }
    /******************************************
     *           Publish Window
     ******************************************* */
    var add_Id : String? by mutableStateOf(null)
    val publishFormState = MutableStateFlow<PublishFormData>(PublishFormData("","","",""))


    /******************************************
     *           Setting Window
     ******************************************* */
    val settingState = MutableStateFlow<SettingWindowData?>(null)
    fun getSetting(){

    }
}