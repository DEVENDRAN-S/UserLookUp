package com.example.userlookup.lookupfeature.model

import com.example.userlookup.DbUser
import com.example.userlookup.common.contentValue
import com.example.userlookup.common.longValue
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

fun JsonArray.parseGetUserResponseToDomainObjectList():List<User>
{
    return jsonArray.map {it.jsonObjectToUserDomainObject() }
}
fun JsonElement.jsonObjectToUserDomainObject():User
{
    val userObject = this.jsonObject
    val addressObject = userObject[User.ADDRESS]!!.jsonObject
    val geoObject = addressObject[Address.GEO]!!.jsonObject
    val companyObject = userObject[User.COMPANY]!!.jsonObject


    return User(
        userid = userObject[User.ID]!!.longValue,
        name = userObject[User.NAME]!!.contentValue,
        userName = userObject[User.USER_NAME]!!.contentValue,
        email = userObject[User.EMAIL]!!.contentValue,
        address = Address(
            street = addressObject[Address.STREET]!!.contentValue,
            suite = addressObject[Address.SUITE]!!.contentValue,
            city = addressObject[Address.CITY]!!.contentValue,
            zipcode = addressObject[Address.ZIPCODE]!!.contentValue,
            geo = Geo(
                lat = geoObject[Geo.LATITUDE]!!.contentValue,
                lng = geoObject[Geo.LONGITUDE]!!.contentValue
            )
        ),
        phone = userObject[User.PHONE]!!.contentValue,
        company= Company(
            companyName = companyObject[Company.COMPANY_NAME]!!.contentValue,
            catchPhrase = companyObject[Company.CATCH_PHASE]!!.contentValue,
            bs =  companyObject[Company.BS]!!.contentValue
        )

    )
}

fun User.toDatalayerObject()= DbUser(
    userId = this.userid,
    name = this.name,
    userName = this.userName,
    email = this.email,
    phone = this.phone,
    street = this.address.street,
    suite = this.address.suite,
    city = this.address.city,
    zipcode = this.address.zipcode,
    latitude = this.address.geo.lat,
    longitude = this.address.geo.lng,
    companyName = this.company.companyName,
    catchPhrase = this.company.catchPhrase,
    bs = this.company.bs
)
fun DbUser.toDomainLayerObject()= User(
    userid= this.userId,
    name = this.name,
    userName = this.userName,
    email = this.email,
    address = Address(
        street = this.street,
        suite = this.suite,
        city = this.city,
        zipcode = this.zipcode,
        geo = Geo(
            lat = this.latitude,
            lng = this.longitude
        )
    ),
    phone = this.phone,
    company= Company(
        companyName = this.companyName,
        catchPhrase = this.catchPhrase,
        bs =  this.bs
    )
)