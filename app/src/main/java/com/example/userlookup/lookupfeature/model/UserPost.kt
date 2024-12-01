package com.example.userlookup.lookupfeature.model

data class UserPost(
    val userId:Long,
    val postId:Long,
    val title:String,
    val body:String
)
{
    companion object{
        const val USER_ID="userId"
        const val POST_ID="id"
        const val TITLE="title"
        const val BODY="body"
    }
}
