package com.example.userlookup.lookupfeature.presentation.util

import com.example.userlookup.lookupfeature.model.User
import com.example.userlookup.lookupfeature.model.UserPost

sealed class UserLookupUIState {
    object Loading : UserLookupUIState()
    object NoData : UserLookupUIState()
    object Initial : UserLookupUIState()
    data class Error(val message: String) : UserLookupUIState()
    data class GotData(val data: User) : UserLookupUIState()
}
sealed class PostLookupUIState {
    object Loading : PostLookupUIState()
    object NoData : PostLookupUIState()
    object Initial : PostLookupUIState()
    data class Error(val message: String) : PostLookupUIState()
    data class GotData(val data: List<UserPost>) : PostLookupUIState()

}