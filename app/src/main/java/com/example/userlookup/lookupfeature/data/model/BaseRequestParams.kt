package com.example.userlookup.lookupfeature.data.model

import com.example.userlookup.common.GetUseCaseCommunicator
import com.example.userlookup.lookupfeature.model.User
import com.example.userlookup.lookupfeature.model.UserPost


open class BaseRequestParams(val itemId: Long)

data class GetUsersRequestParam(val userName:String):BaseRequestParams(0L)
data class GetUsersResponseValue(val user: User)

data class GetUserPostsRequestParam(private val userId:Long):BaseRequestParams(userId)
data class GetUserPostsResponseValue(val userPostList:List<UserPost>)

typealias GetUsersListingCommunicator = GetUseCaseCommunicator<GetUsersRequestParam,GetUsersResponseValue>
typealias GetPostsListingCommunicator = GetUseCaseCommunicator<GetUserPostsRequestParam, GetUserPostsResponseValue>
