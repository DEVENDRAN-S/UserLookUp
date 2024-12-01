package com.example.userlookup.lookupfeature.data.source

import com.example.userlookup.common.APIResult
import com.example.userlookup.common.APIParseUtil
import com.example.userlookup.common.UserLookUpAPI
import com.example.userlookup.common.addParams
import com.example.userlookup.common.getURLPath
import com.example.userlookup.common.httpClient

import com.example.userlookup.lookupfeature.data.model.BaseRequestParams

import io.ktor.client.request.get

class UserLookUpRemoteDataSource {

    suspend fun getUsersFromServer(): APIResult
    {
        val url = StringBuilder(getURLPath(UserLookUpAPI.GET_USER))
        return handleGet(url.toString())
    }
    suspend fun getUserPostsFromServer(requestValues: BaseRequestParams): APIResult
    {
        val url = StringBuilder(getURLPath(UserLookUpAPI.GET_USER_POST))
        url.addParams("userId", requestValues.itemId)
        return handleGet(url.toString())
    }


    private suspend fun handleGet(urlBuilder: String): APIResult
    {
        return try{
            val httpResponse = httpClient.get(urlBuilder)
            APIParseUtil.processResponse(httpResponse)
        } catch(ex: Exception){
            APIResult.HasError("API Exception Occured")
        }
    }

    companion object
    {
        private var INSTANCE: UserLookUpRemoteDataSource? = null
        fun getInstance(): UserLookUpRemoteDataSource
        {
            if (INSTANCE == null)
            {
                INSTANCE = UserLookUpRemoteDataSource()
            }
            return INSTANCE!!
        }
    }
}