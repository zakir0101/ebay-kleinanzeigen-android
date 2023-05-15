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

    private fun <T> checkNullability(funName: String, result: T?): Boolean {
        internetConencted = true

        if (result != null)
            Log.v(ClassName, "$funName() successfully ")
        else
            Log.e(ClassName, "$funName() result is null")

        return result != null
    }

    private fun onIOException(funName: String, e: Exception) {
        Log.e(ClassName, "netwrok error when calling $funName() :$e")
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
        val funName = "isUserLogged"
        viewModelScope.launch {
            try {
                val result = ebayRepository.isUserLogged()
                loginState.value = result
                if (checkNullability(funName, result))
                    getMain()
            } catch (e: IOException) {
                onIOException(funName, e)
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
        mainState.value = null
        viewModelScope.launch {
            try {
                val result = ebayRepository.getMainData()
                mainState.value = result
                checkNullability(funName, result)
            } catch (e: IOException) {
                onIOException(funName, e)
            }
        }
    }

    /******************************************
     *           Add Window
     ******************************************* */
    var activeAddLink by mutableStateOf<String?>(null)
    val addWindowState = MutableStateFlow<AddWindowData?>(null)
    var addWindowContactName by mutableStateOf("")
    var addWindowMessage by mutableStateOf("")
    var addWindowSending by mutableStateOf(false)
    val addWindowSendMessageResponse = MutableStateFlow<SendMessageResponse?>(null)
    fun getAddData() {
        val funName = "getAddData"
        if (activeAddLink == null) {
            return
        }
        addWindowState.value = null
        addWindowSendMessageResponse.value = null
        viewModelScope.launch {
            try {
                val result = ebayRepository.getAddData(activeAddLink!!)
                addWindowState.value = result
                checkNullability(funName, result)
            } catch (e: IOException) {
                onIOException(funName, e)
            }
        }
    }

    fun sendMessageFromAddPage() {
        if (addWindowState.value == null)
            return
        val funName = "sendMessageFromAddPage"
        addWindowSendMessageResponse.value = null
        addWindowSending = true
        viewModelScope.launch {
            try {
                val result = ebayRepository.sendMessageFromAddPage(
                    addWindowMessage,
                    addWindowContactName,
                    addWindowState.value!!
                )
                addWindowSendMessageResponse.value = result
                checkNullability(funName, result)
                addWindowSending = false
                if (result != null && result.status.lowercase() == "ok") {
                    addWindowMessage = ""
                    addWindowContactName = ""
                }

            } catch (e: IOException) {
                onIOException(funName, e)
            }
        }
    }

    /******************************************
     *           User Window
     ******************************************* */
    var activeUserLink by mutableStateOf<String?>(null)
    val userWindowState = MutableStateFlow<UserWindowData?>(null)
    fun getUserData() {
        if (activeUserLink == null)
            return
        userWindowState.value = null
        val funName = "getUserData"
        viewModelScope.launch {
            try {
                val result = ebayRepository.getUserData(activeUserLink!!)
                userWindowState.value = result
                checkNullability(funName, result)
            } catch (e: IOException) {
                onIOException(funName, e)
            }
        }
    }

    /******************************************
     *           Search Window
     ******************************************* */
    val searchWindowData = MutableStateFlow<SearchWindowData?>(null)
    val filterState = MutableStateFlow<FilterData>(FilterData())
    var filterActiveCity = MutableStateFlow<City>(deutschland)
    var filterActiveCategory = MutableStateFlow<Category>(allCategories)
    fun getSearchResult() {

        val funName = "getSearchResult"
        searchWindowData.value = null
        viewModelScope.launch {
            try {
                val result = ebayRepository.getSearchResults(
                    filterActiveCity.value,
                    filterActiveCategory.value,
                    filterState.value
                )
                searchWindowData.value = result
                checkNullability(funName, result)
            } catch (e: IOException) {
                onIOException(funName, e)
            }
        }
    }

    /******************************************
     *           Categories
     ******************************************* */
    val categories = MutableStateFlow<List<Category>?>(null)
    fun getCategories() {
        val funName = "getCategories"
        viewModelScope.launch {
            try {
                val result = ebayRepository.getCategories()
                categories.value = result
                checkNullability(funName, result)
            } catch (e: IOException) {
                onIOException(funName, e)
            }
        }

    }

    /******************************************
     *           Cities
     ******************************************* */
    var citySearch by mutableStateOf("")
    val citiesList = MutableStateFlow<List<City>?>(null)
    fun getCities() {
        if (citySearch.isBlank() || citySearch.length < 2)
            return
        val funName = "getCities"
        viewModelScope.launch {
            try {
                val result = ebayRepository.getCities(citySearch)
                citiesList.value = result
                checkNullability(funName, result)
            } catch (e: IOException) {
                onIOException(funName, e)
            }
        }

    }

    /******************************************
     *           Conversation Window
     ******************************************* */
    val conversationWindowState = MutableStateFlow<ConversationWindowData?>(null)
    var conversationSize = (100)
    var conversationPage = 0

    fun getConversation() {
        if (loginState.value == null || !loginState.value!!.is_logged)
            return
        conversationWindowState.value = null
        val funName = "getConversation"
        viewModelScope.launch {
            try {
                val result = ebayRepository.getConversationData(
                    loginState.value!!.user_id,
                    conversationPage.toString(),
                    conversationSize.toString()
                )
                conversationWindowState.value = result
                checkNullability(funName, result)
            } catch (e: IOException) {
                onIOException(funName, e)
            }
        }

    }

    fun getHisLink(con: Conversation): String {
        val hisId = if (con.role == "Seller") con.userIdBuyer else con.userIdSeller
        return "/s-bestandsliste.html?userId=" + hisId
    }

    fun getHisName(con: Conversation): String {
        return if (con.role == "Seller") con.buyerName else con.sellerName

    }

    fun getMyName(con: Conversation): String {
        return if (con.role == "Seller") con.sellerName else con.buyerName
    }

    fun getHisInitials(con: Conversation): String {
        return if (con.role == "Seller") con.buyerInitials else con.sellerInitials
    }

    fun getMyInitials(con: Conversation): String {
        return if (con.role == "Seller") con.sellerInitials else con.buyerInitials
    }

    /******************************************
     *           Message Window
     ******************************************* */
    var messageWindowMessage by mutableStateOf("")
    var messageWindowResoponse = MutableStateFlow<SendMessageResponse?>(null)
    var activeConversation = MutableStateFlow<Conversation?>(null)
    var activeConversationId: String? = null
    var messageWindowSending by mutableStateOf(false)
    fun getMessages() {
        if (loginState.value == null || !loginState.value!!.is_logged || activeConversationId == null)
            return
        val funName = "getMessages"
        activeConversation.value = null
        viewModelScope.launch {
            try {
                val result =
                    ebayRepository.getMessages(loginState.value!!.user_id, activeConversationId!!)
                activeConversation.value = result
                checkNullability(funName, result)
                messageWindowSending = false
            } catch (e: IOException) {
                onIOException(funName, e)
            }
        }
    }

    fun sendMessageFromMessageBox() {
        val funName = "sendMessageFromMessageBox"

        if (loginState.value == null || !loginState.value!!.is_logged || activeConversationId == null || messageWindowMessage.isBlank()) {
            Log.e(
                funName,
                "parameter not suffeciant for method : ${activeConversationId.toString()}"
            )
            return
        }
        messageWindowSending = true
        viewModelScope.launch {
            try {
                val result = ebayRepository.sendMessageFromMessageBox(
                    messageWindowMessage,
                    loginState.value!!.user_id,
                    activeConversationId!!
                )

                messageWindowResoponse.value = result
                checkNullability(funName, result)
                messageWindowSending = false
                if (result != null && result.status.lowercase() == "ok") {
                    messageWindowMessage = ""
                    activeConversation.value = null
                    getConversation()
                    getMessages()
                }
            } catch (e: IOException) {
                onIOException(funName, e)
            }
        }
    }

    /******************************************
     *           Publish Window
     ******************************************* */
    var addId: String? = null
    val publishFormState = MutableStateFlow<PublishFormData>(PublishFormData("", "", "", ""))
    val formDataCity = MutableStateFlow<City>(deutschland)
    val publishResponse = MutableStateFlow<PublishAddResponse?>(null)
    var publishCurrent: String by mutableStateOf("form")
    var publishFormError by mutableStateOf("")
    var checkResponse = MutableStateFlow<PublishAddResponse?>(null)
    fun publishAdd() {
        val data = publishFormState.value
        if (data.title.isBlank() || data.title.length < 10) {
            publishFormError = "title has to be at least 10 Character"
            return
        }
        if (data.description.isBlank() || data.description.length < 10) {
            publishFormError = "Description has to be at least 10 Character"
            return
        }
        if (data.contact_name.isBlank()) {
            publishFormError = "you have to enter a contact name"
            return
        }
        if (data.price.isBlank() || data.price.toDoubleOrNull() == null) {
            publishFormError = "you have to specify a number for the price"
            return
        }

        if (formDataCity.value.code == deutschland.code) {
            publishFormError = "you have to choose a zip code"
            return
        }
        if (loginState.value == null || !loginState.value!!.is_logged ||
            formDataCity.value == null || data.contact_name.isBlank() || data.description.length < 10
            || data.title.length < 10 || data.price.toDoubleOrNull() == null
        )
            return
        val funName = "publishAdd"
        viewModelScope.launch {
            try {
                val result = ebayRepository.publishAdd(publishFormState.value, formDataCity.value!!)
                publishResponse.value = result
                checkNullability(funName, result)
                if (result != null) {
                    if (result.state == "OK") {
                        addId = result.add_id
                        publishCurrent = "waiting"
                        checkAdd()

                    } else {
                        publishCurrent = "error"
                    }
                }
            } catch (e: IOException) {
                onIOException(funName, e)
            }
        }
    }

    fun checkAdd() {
        if (loginState.value == null || !loginState.value!!.is_logged || addId == null)
            return
        val funName = "checkAdd"
        viewModelScope.launch {
            try {
                val result = ebayRepository.checkAdd(addId!!)
                publishResponse.value = result
                checkNullability(funName, result)
            } catch (e: IOException) {
                onIOException(funName, e)
            }
        }
    }

    /******************************************
     *           My Add
     ******************************************* */
    fun getMyAdd() {
        if (loginState.value == null || !loginState.value!!.is_logged)
            return
        activeUserLink = "/s-bestandsliste.html?userId=" + loginState.value!!.user_id
        getUserData()
    }


    /******************************************
     *           Setting Window
     ******************************************* */
    val settingState = MutableStateFlow<SettingWindowData?>(null)
    fun getSetting() {
        if (loginState.value == null || !loginState.value!!.is_logged)
            return
        val funName = "settingState"
        viewModelScope.launch {
            try {
                val result = ebayRepository.getSettingData()
                settingState.value = result
                checkNullability(funName, result)
            } catch (e: IOException) {
                onIOException(funName, e)
            }
        }
    }
}