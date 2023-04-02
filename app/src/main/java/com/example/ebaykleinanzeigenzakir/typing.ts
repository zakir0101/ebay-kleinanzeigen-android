


interface SearchPage{
  result : SearchResults[],
  alternative : SearchResults[],
  navigation : number
}

interface MainPage {
  main : MainItem [],
  galerie : MainItem[]
}


interface UserPage{
  user_id: string,
  user : User,
  user_adds : SearchResults[]
}


interface AddPage {
  images_url: ImageUrl[],
  title: string,
  price: string,
  shipping:string,
  location: string,
  date: string,
  views: string,
  art: string,
  description: string,
  other_add_by_user: SearchResults[],
  other_add_similar: SearchResults[],
  user : User,
  add_id: string
}

interface ImageUrl{
  image_url:string
}


interface SearchResults {
  image_url: string,
  location: string,
  date : string,
  title: string,
  description: string,
  price: string,
  url_link: string,
  shipping:string,
  direkt_kaufen:string
}

interface MainItem {
  url_link: string,
  title: string,
  location: string,
  price: string,
  image_link: string
}

interface Cookie {
  "domain": string,
  "expirationDate": number,
  "hostOnly": boolean,
  "httpOnly": boolean,
  "name": string,
  "path": string,
  "sameSite": string,
  "secure": boolean,
  "session": boolean,
  "storeId": string,
  "value": string
}

interface City{
  name: string , code:string
}

interface City2{
  name: string , id:string
}

interface Category {
  name:string,
  url_link: string,
  code :  string,
  children:Category[]
}


interface User{
  user_name: string,
  user_logo:string,
  user_link: string,
  rating: string,
  friendliness: string,
  reliability: string,
  reply_rate:string,
  reply_speed:string,
  followers:string,
  user_ad_number: string,
  user_type: string,
  active_since:string,
}

interface ConversationPage{
  conversations: Conversation[],
  _links : Link,
  _meta : Meta,
}

interface Link{
  next : string,
  current : string
}
interface Meta{
  numFound : number
}

// role either Seller or Buyer
interface Conversation{
  adId : string,
  adImage : string,
  adStatus : string,
  adTitle : string,
  adType : string,
  buyerInitials: string,
  buyerName : string ,
  id : string,
  role : string,
  sellerName : string,
  sellerInitials : string,
  textShortTrimmed : string,
  unreadMessagesCount:number,
  userIdBuyer:string,
  userIdSeller : string
  messages : Message[]

}

// boundness either OUTBOUND or INBOUND
interface Message{
  boundness : string,
  messageId : string,
  receiveDate : string,
  readableDate : string,
  textShort : string,
  title : string,
  type : string
}


interface Login {
  is_logged:boolean,
  user_id:string,
  user_name :string,
  user_email : string
}

interface SettingPage{
  add_num_online : string,
  add_num_total : string
}
export {SearchResults,SearchPage, UserPage,MainPage ,MainItem,
  Cookie ,City,Category,User,AddPage,ImageUrl,ConversationPage,
Link,Meta,Message,Conversation,City2,Login,SettingPage }
