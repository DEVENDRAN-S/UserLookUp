package com.example.userlookup.lookupfeature.data.source

import com.example.userlookup.BaseApplication
import com.example.userlookup.common.APIResult
import com.example.userlookup.lookupfeature.data.model.GetPostsListingCommunicator
import com.example.userlookup.lookupfeature.data.model.GetUserPostsRequestParam
import com.example.userlookup.lookupfeature.data.model.GetUserPostsResponseValue
import com.example.userlookup.lookupfeature.data.model.GetUsersListingCommunicator
import com.example.userlookup.lookupfeature.data.model.GetUsersRequestParam
import com.example.userlookup.lookupfeature.data.model.GetUsersResponseValue
import com.example.userlookup.lookupfeature.data.model.UserLookUpDataSource
import com.example.userlookup.lookupfeature.model.parseGetUserPostResponseToDomainObjectList
import com.example.userlookup.lookupfeature.model.parseGetUserResponseToDomainObjectList
import com.example.userlookup.util.AppConstants
import com.example.userlookup.util.NetworkUtil
import kotlinx.serialization.json.jsonArray

class UserLookUpRepository(
    private val userLookUpLocalDataSource: UserLookUpLocalDataSource,
    private val userLookUpRemoteDataSource: UserLookUpRemoteDataSource
): UserLookUpDataSource {

    override suspend fun getUsers(
        requestValues: GetUsersRequestParam,
        callType: Int,
        callBack: GetUsersListingCommunicator
    ) {
        when(callType){

            AppConstants.LOCAL_AND_REMOTE-> getUserFromRemote(requestValues,callBack)

            AppConstants.LOCAL_ONLY->  getUserFromLocal(requestValues,callBack)
        }
    }

    private suspend fun getUserFromRemote(requestValues: GetUsersRequestParam, getUserDataCallBack: GetUsersListingCommunicator)
    {

        if(NetworkUtil.isNetworkAvailable())
        {
            getUserDataCallBack.serverCallInitiated(requestValues)
            val response = userLookUpRemoteDataSource.getUsersFromServer()
            val currentTime = System.currentTimeMillis()
            BaseApplication.instance!!.setLastSyncTimeBasedOnPreference(key=AppConstants.KEY_USERS_LIST,value=currentTime)
            when(response)
            {
                is APIResult.HasNoData -> getUserDataCallBack.noDataFound(requestValues)
                is APIResult.HasError -> getUserDataCallBack.errorOccurred(requestValues,response.error)
                is APIResult.APIData ->{
                    val responseArray = response.apiData.jsonArray
                    val userList = responseArray.parseGetUserResponseToDomainObjectList()
                    userLookUpLocalDataSource.deleteAllUsers()
                    userLookUpLocalDataSource.insertUserListIntoDB(userList)
                    getUserFromLocal(requestValues,getUserDataCallBack)
                }

                else -> {}
            }
        }
        else
        {
            getUserFromLocal(requestValues,getUserDataCallBack)
        }
    }
    private fun getUserFromLocal(
        requestValues: GetUsersRequestParam,
        callBack: GetUsersListingCommunicator
    )
    {
        val isUserExist = userLookUpLocalDataSource.checkUserExist(requestValues.userName)
        if(isUserExist)
        {
            val user = userLookUpLocalDataSource.getUserFromLocal(requestValues.userName)
            callBack.gotData(requestValues, GetUsersResponseValue(user))
        }
        else{
            callBack.noDataFound(requestValues)
        }
    }

    override suspend fun getPostByUser(
        requestValues: GetUserPostsRequestParam,
        callType: Int,
        callBack: GetPostsListingCommunicator
    ) {
        when(callType){

            AppConstants.LOCAL_AND_REMOTE-> getUserPostFromRemote(requestValues,callBack)

            AppConstants.LOCAL_ONLY->  getUserPostFromLocal(requestValues,callBack)
        }
    }

    private suspend fun getUserPostFromRemote(requestValues: GetUserPostsRequestParam, getUserPostCallBack: GetPostsListingCommunicator)
    {
        if(NetworkUtil.isNetworkAvailable())
        {
            getUserPostCallBack.serverCallInitiated(requestValues)
            val response = userLookUpRemoteDataSource.getUserPostsFromServer(requestValues)
            val currentTime = System.currentTimeMillis()
            BaseApplication.instance!!.setLastSyncTimeBasedOnPreference(key=AppConstants.KEY_USER_POSTS_LIST, itemId = requestValues.itemId.toString(), value = currentTime)
            when(response)
            {
                is APIResult.HasNoData -> getUserPostCallBack.noDataFound(requestValues)
                is APIResult.HasError -> getUserPostCallBack.errorOccurred(requestValues,response.error)
                is APIResult.APIData ->{
                    val responseArray = response.apiData.jsonArray
                    val userPostList = responseArray.parseGetUserPostResponseToDomainObjectList()
                    userLookUpLocalDataSource.deletePostByUser(requestValues.itemId)
                    userLookUpLocalDataSource.insertUserPostsListIntoDB(userPostList)
                    getUserPostFromLocal(requestValues,getUserPostCallBack)
                }

                else -> {}
            }
        }
        else
        {
            getUserPostFromLocal(requestValues,getUserPostCallBack)
        }
    }
    private fun getUserPostFromLocal(
        requestValues: GetUserPostsRequestParam,
        callBack: GetPostsListingCommunicator
    ) {
        val userPostList = userLookUpLocalDataSource.getUserPostsFromLocal(requestValues.itemId)
        if(userPostList.isNotEmpty())
        {
            callBack.gotData(requestValues, GetUserPostsResponseValue(userPostList))
        }
        else{
            callBack.noDataFound(requestValues)
        }

    }
}