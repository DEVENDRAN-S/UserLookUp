package com.example.userlookup.lookupfeature.domain

import com.example.userlookup.BaseApplication
import com.example.userlookup.common.UsecaseContract
import com.example.userlookup.lookupfeature.data.model.GetPostsListingCommunicator
import com.example.userlookup.lookupfeature.data.model.GetUserPostsRequestParam
import com.example.userlookup.lookupfeature.data.model.UserLookUpDataSource
import com.example.userlookup.util.AppConstants

class GetUserPostsUseCase(private var userLookUpDataSource: UserLookUpDataSource): UsecaseContract<GetUserPostsRequestParam, GetPostsListingCommunicator> {

    override suspend fun execute(request: GetUserPostsRequestParam, callback: GetPostsListingCommunicator) {
        executeGetPosts(request,callback)
    }
    private suspend fun executeGetPosts(requestValues: GetUserPostsRequestParam, callback: GetPostsListingCommunicator)
    {
        val lastSync = BaseApplication.instance!!.getLastSyncTimeBasedOnPreference(AppConstants.KEY_USER_POSTS_LIST,requestValues.itemId.toString())
        val requestType = when {
            System.currentTimeMillis() - lastSync > (AppConstants.TIME_LIMIT_FOR_REMOTE_CALL)  -> AppConstants.LOCAL_AND_REMOTE
            else -> AppConstants.LOCAL_ONLY
        }
        callToDataSourceForGetPostsData(requestValues,requestType,callback)
    }

    private suspend fun callToDataSourceForGetPostsData(requestValues:GetUserPostsRequestParam, requestType:Int, callback: GetPostsListingCommunicator)
    {
        userLookUpDataSource.getPostByUser(requestValues,requestType,callback)
    }
}