package com.example.userlookup.lookupfeature.model

import com.example.userlookup.DbPost
import com.example.userlookup.common.contentValue
import com.example.userlookup.common.longValue
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

fun JsonArray.parseGetUserPostResponseToDomainObjectList():List<UserPost>
{
    return jsonArray.map {it.jsonObjectToUserPostDomainObject() }
}
fun JsonElement.jsonObjectToUserPostDomainObject():UserPost
{
    val userPostObject = this.jsonObject
    return UserPost(
        userId = userPostObject[UserPost.USER_ID]!!.longValue,
        postId = userPostObject[UserPost.POST_ID]!!.longValue,
        title = userPostObject[UserPost.TITLE]!!.contentValue,
        body = userPostObject[UserPost.BODY]!!.contentValue)
}
fun UserPost.toDatalayerObject()= DbPost(
    userId = this.userId,
    postId = this.postId,
    title = this.title,
    body = this.body
)
fun DbPost.toDomainLayerObject()= UserPost(
    userId = this.userId,
    postId = this.postId,
    title = this.title,
    body = this.body
)