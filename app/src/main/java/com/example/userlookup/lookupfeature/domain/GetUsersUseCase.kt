package com.example.userlookup.lookupfeature.domain


import com.example.userlookup.BaseApplication
import com.example.userlookup.common.UsecaseContract
import com.example.userlookup.lookupfeature.data.model.GetUsersListingCommunicator
import com.example.userlookup.lookupfeature.data.model.GetUsersRequestParam
import com.example.userlookup.lookupfeature.data.model.UserLookUpDataSource
import com.example.userlookup.util.AppConstants.KEY_USERS_LIST
import com.example.userlookup.util.AppConstants.LOCAL_AND_REMOTE
import com.example.userlookup.util.AppConstants.LOCAL_ONLY
import com.example.userlookup.util.AppConstants.TIME_LIMIT_FOR_REMOTE_CALL

class GetUsersUseCase (private var userLookUpDataSource: UserLookUpDataSource): UsecaseContract<GetUsersRequestParam, GetUsersListingCommunicator> {

    override suspend fun execute(request: GetUsersRequestParam, callback: GetUsersListingCommunicator) {
        executeGetUsers(request,callback)
    }
    private suspend fun executeGetUsers(requestValues: GetUsersRequestParam, callback: GetUsersListingCommunicator)
    {
        val lastSync = BaseApplication.instance!!.getLastSyncTimeBasedOnPreference( KEY_USERS_LIST)
        val requestType = when {
            System.currentTimeMillis() - lastSync > (TIME_LIMIT_FOR_REMOTE_CALL)  ->  LOCAL_AND_REMOTE
            else -> LOCAL_ONLY
        }
        callToDataSourceForGetUserData(requestValues,requestType,callback)
    }

    private suspend fun callToDataSourceForGetUserData(requestValues:GetUsersRequestParam, requestType:Int, callback: GetUsersListingCommunicator)
    {
        userLookUpDataSource.getUsers(requestValues,requestType,callback)
    }

}