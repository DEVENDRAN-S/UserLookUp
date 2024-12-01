package com.example.userlookup.common

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonPrimitive



val httpClient: HttpClient
    get() = HttpClient(OkHttp) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }


enum class UserLookUpAPI(val path: String)  {
    GET_USER("https://jsonplaceholder.typicode.com/users?"),
    GET_USER_POST("https://jsonplaceholder.typicode.com/posts?");

}
fun getURLPath(userLookUpAPI:UserLookUpAPI):String
{
    return userLookUpAPI.path
}
fun StringBuilder.addParams(param: String, value: Any?)
{
    value?.let {
        append(param).append("=").append(value.toString()) //No I18N
    }
}


inline val JsonElement.contentValue: String
    get() = this.jsonPrimitive.content
inline val JsonElement.longValue: Long
    get() = if (this.contentValue == "") -1 else this.jsonPrimitive.content.toLong()
inline val HttpStatusCode.isErrorStatus: Boolean
    get() =  (this == HttpStatusCode.OK || this == HttpStatusCode.Created || this == HttpStatusCode.Accepted).not()
internal val remoteRepoJson
    get() = Json { useArrayPolymorphism = true; ignoreUnknownKeys = true; isLenient = true; coerceInputValues = true }