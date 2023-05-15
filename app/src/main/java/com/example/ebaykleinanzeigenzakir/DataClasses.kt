package com.example.ebaykleinanzeigenzakir

import androidx.compose.ui.graphics.vector.ImageVector
import java.util.*

data class MenuItem(
    val name: String,
    val value: String,
    val icon: ImageVector,
    val action: () -> Unit
)


/******************************************
 *           Login Window
 ******************************************* */

data class LoginData(
    val is_logged: Boolean,
    val user_id: String,
    val user_name: String,
    val user_email: String
)

data class ErrorResponse(
    val type : String,
    val msg : String
)

/******************************************
 *           Main Window
 ******************************************* */

data class MainItemData(
    val url_link: String,
    val title: String,
    val location: String,
    val price: String,
    val image_link: String
)

data class MainWindowData(
    val main: List<MainItemData>,
    val galerie: List<MainItemData>
)


/******************************************
 *           Add Window
 ******************************************* */

data class AddWindowData(
    val images_url: List<ImageUrl>,
    val title: String,
    val price: String,
    val shipping: String,
    val location: String,
    val date: String,
    val views: String,
    val art: String,
    val description: String,
    val other_add_by_user: List<SearchResults>,
    val other_add_similar: List<SearchResults>,
    val user: User,
    val add_id: String
)

data class ImageUrl(
    val image_url: String
)

/******************************************
 *           User Window
 ******************************************* */
data class User(
    val user_name: String,
    val user_logo: String,
    val user_link: String,
    val rating: String,
    val friendliness: String,
    val reliability: String,
    val reply_rate: String,
    val reply_speed: String,
    val followers: String,
    val user_ad_number: String,
    val user_type: String,
    val active_since: String
)

data class UserWindowData(
    val user_id: String,
    val user: User,
    val user_adds: List<SearchResults>
)


/******************************************
 *           Search Window
 ******************************************* */

data class SearchResults(
    val image_url: String,
    val location: String,
    val date: String,
    val title: String,
    val description: String,
    val price: String,
    val url_link: String,
    val shipping: String,
    val direkt_kaufen: String
)

data class SearchWindowData(
    val result: List<SearchResults>,
    val alternative: List<SearchResults>,
    val navigation: Int
)
val allCategories:Category = Category(name = "alle Kategorien",
                url_link = "https://www.ebay-kleinanzeigen.de/s-suchen.html"
                ,code = "", children = mutableListOf() )

val deutschland:City = City(name="Deutschland - ",code="0" ,zip = "" )

data class FilterData(
    var search : String = "",
    val priceFrom : String = "",
    val priceTo : String = "",
    val anbieter : String = "",
    val anzeige : String = "",
    val direktKaufen : String = "",
    val paketdient : String = "",
    val activeRange : String = "",

    )

/******************************************
 *           Conversation Window
 ******************************************* */
data class ConversationWindowData(
    val conversations: List<Conversation>,
//    val _links: Link,
    val _meta: Meta
)

data class Link(
    val next: String,
    val current: String
)

data class Meta(
    val numFound: Int
)

data class Conversation(
    val adId: String,
    val adImage: String? ,
    val adStatus: String,
    val adTitle: String,
    val adType: String,
    val buyerInitials: String,
    val buyerName: String,
    val id: String,
    val role: String,
    val sellerName: String,
    val sellerInitials: String,
    val textShortTrimmed: String,
    val unreadMessagesCount: Int,
    val userIdBuyer: String,
    val userIdSeller: String,
    val messages: List<Message>
)

data class Message(
    val boundness: String,
    val messageId: String,
    val receiveDate: String,
    val readableDate: String,
    val textShort: String,
    val title: String,
    val type: String
)


data class SendMessageResponse(
    val status : String
)

/******************************************
 *           publish Window
 ******************************************* */
data class PublishFormData(
    val title : String ,
    val price : String,
    val description: String,
    val contact_name : String,
)

data class PublishAddResponse(
    val state : String,
    val add_id : String = "",
    val html : String = "",
    val link : String = ""
)
/******************************************
 *           Setting Window
 ******************************************* */


data class SettingWindowData(
    val add_num_online: String,
    val add_num_total: String
)


/******************************************
 *           Cookie
 ******************************************* */

data class Cookie(
    val domain: String,
    val expirationDate: Double,
    val hostOnly: Boolean,
    val httpOnly:Boolean,
    val name: String,
    val value: String,
    val path: String,
    val sameSite: String ,
    val secure: Boolean,
    val session: Boolean,
    val storeId: String
    ){

    override fun equals(other: Any?): Boolean{
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Cookie

        if (name != other.name ) return false

        return true
    }

    override fun hashCode(): Int{
        return name.hashCode()
    }
}


/******************************************
 *           City
 ******************************************* */

data class City (
    val name : String,
    val code : String,
    var zip : String
        )

/******************************************
 *           Category
 ******************************************* */

data class Category(
    val name : String,
    val url_link : String,
    val code : String,
    val children : List<Category>
)

