package com.example.userlookup.common

import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject

object APIParseUtil {
    suspend fun processResponse(httpResponse: HttpResponse): APIResult {
        return try {

            when  {
                httpResponse.status == HttpStatusCode.NoContent -> {
                    val msg = httpResponse.status.description
                    return APIResult.HasNoData
                }
                httpResponse.status.isErrorStatus -> {
                    val msg = httpResponse.status.description
                    return APIResult.HasError(msg)
                }
            }
            val rawString = httpResponse.bodyAsText()

            val jsonArray = lazy {
                remoteRepoJson.decodeFromString(
                    JsonArray.serializer(),
                    rawString
                )
            }
            val result = when {
                jsonArray.value == null -> APIResult.HasError("API Data error")
                jsonArray.value.isEmpty() -> APIResult.HasNoData
                else -> APIResult.APIData(jsonArray.value)
            }

            result

        } catch (e: Exception) {
            APIResult.HasError("API Data error")
        }
    }
}