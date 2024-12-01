package com.example.userlookup.lookupfeature.model

data class User(
    val userid:Long,
    val name:String,
    val userName:String,
    val email:String,
    val address:Address,
    val phone:String,
    val company:Company,
)
{
    companion object {
        const val ID = "id"
        const val NAME = "name"
        const val USER_NAME = "username"
        const val EMAIL = "email"
        const val PHONE= "phone"
        const val ADDRESS ="address"
        const val COMPANY ="company"
    }
}
data class Address(
    val street:String,
    val suite:String,
    val city:String,
    val zipcode:String,
    val geo:Geo,
)
{
    companion object{
        const val STREET = "street"
        const val SUITE = "suite"
        const val CITY = "city"
        const val ZIPCODE = "zipcode"
        const val GEO = "geo"
    }

}
data class Company(
    val companyName:String,
    val catchPhrase:String,
    val bs:String,
)
{
    companion object{
        const val COMPANY_NAME = "name"
        const val CATCH_PHASE = "catchPhrase"
        const val BS = "bs"
    }

}
data class Geo(
    val lat:String,
    val lng:String
)
{
    companion object{
        const val LATITUDE = "lat"
        const val LONGITUDE = "lng"
    }

}