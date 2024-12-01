package com.example.userlookup.lookupfeature.data.model


interface UserLookUpDataSource {
    suspend fun getUsers(
        requestValues:GetUsersRequestParam,
        callType: Int,
        callBack: GetUsersListingCommunicator
    )
    suspend fun getPostByUser(
        requestValues:GetUserPostsRequestParam,
        callType: Int,
        callBack: GetPostsListingCommunicator
    )
}
