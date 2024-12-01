package com.example.userlookup.lookupfeature.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlookup.lookupfeature.data.model.GetPostsListingCommunicator
import com.example.userlookup.lookupfeature.data.model.GetUserPostsRequestParam
import com.example.userlookup.lookupfeature.data.model.GetUserPostsResponseValue
import com.example.userlookup.lookupfeature.data.model.GetUsersListingCommunicator
import com.example.userlookup.lookupfeature.data.model.GetUsersRequestParam
import com.example.userlookup.lookupfeature.data.model.GetUsersResponseValue
import com.example.userlookup.lookupfeature.factory.GetUserPostsFactory
import com.example.userlookup.lookupfeature.factory.GetUsersFactory
import com.example.userlookup.lookupfeature.model.Address
import com.example.userlookup.lookupfeature.model.Company
import com.example.userlookup.lookupfeature.model.Geo
import com.example.userlookup.lookupfeature.model.User
import com.example.userlookup.lookupfeature.model.UserPost
import com.example.userlookup.lookupfeature.presentation.util.PostLookupUIState
import com.example.userlookup.lookupfeature.presentation.util.UserLookupUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserLookUpViewModel:ViewModel() {
    var userlookUpUIState by mutableStateOf<UserLookupUIState>(UserLookupUIState.Initial)
    var postlookUpUIState by mutableStateOf<PostLookupUIState>(PostLookupUIState.Initial)

    var userData by mutableStateOf<User?>(null)

    private  val getUserdataEngine  = GetUsersFactory.createInstance()
    private  val getUserPostsEngine  = GetUserPostsFactory.createInstance()

    private lateinit var getUserCommunicator: GetUsersListingCommunicator
    private lateinit var getUserPostCommunicator: GetPostsListingCommunicator
    var userPost: SnapshotStateList<UserPost> = mutableStateListOf()


    init{
        initListener()
    }

    fun fetchUser(user:String) {
        viewModelScope.launch(Dispatchers.IO) {
            getUserdataEngine.execute(request = GetUsersRequestParam(userName = user), callback = getUserCommunicator)
        }
    }
    fun fetchUserPosts(userId:Long){
        userPost.clear()
        viewModelScope.launch(Dispatchers.IO) {
            getUserPostsEngine.execute(request = GetUserPostsRequestParam(userId), callback = getUserPostCommunicator)
        }
    }
    fun setUserLookupStateToInitial()
    {
        userlookUpUIState = UserLookupUIState.Initial
    }
    fun setPostLookupStateToInitial()
    {
        postlookUpUIState = PostLookupUIState.Initial
    }
    private fun initListener()
    {
        getUserCommunicator = object :GetUsersListingCommunicator{
            override fun serverCallInitiated(request: GetUsersRequestParam) {
                userlookUpUIState = UserLookupUIState.Loading
            }

            override fun errorOccurred(request: GetUsersRequestParam, error: String) {
                userlookUpUIState = UserLookupUIState.Error(error)
            }

            override fun noDataFound(request: GetUsersRequestParam) {
                userlookUpUIState = UserLookupUIState.NoData

            }

            override fun gotData(request: GetUsersRequestParam, data: GetUsersResponseValue) {
                userData = data.user
                userlookUpUIState = UserLookupUIState.GotData(data.user)

            }
        }

        getUserPostCommunicator = object:GetPostsListingCommunicator{
            override fun serverCallInitiated(request: GetUserPostsRequestParam) {
                postlookUpUIState = PostLookupUIState.Loading
            }

            override fun errorOccurred(request: GetUserPostsRequestParam, error: String) {
                postlookUpUIState = PostLookupUIState.Error(error)

            }

            override fun noDataFound(request: GetUserPostsRequestParam) {
                postlookUpUIState = PostLookupUIState.NoData
            }

            override fun gotData(
                request: GetUserPostsRequestParam,
                data: GetUserPostsResponseValue
            ) {
                userPost.addAll(data.userPostList)
                postlookUpUIState = PostLookupUIState.GotData(data.userPostList)
            }
        }
    }

}