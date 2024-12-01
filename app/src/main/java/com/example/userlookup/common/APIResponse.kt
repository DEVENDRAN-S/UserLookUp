package com.example.userlookup.common

import kotlinx.serialization.json.JsonElement


sealed interface APIResult {
    data class APIData(val apiData: JsonElement): APIResult
    object HasNoData: APIResult
    data class HasError(val error: String): APIResult
}