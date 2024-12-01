package com.example.userlookup.lookupfeature.data.source

import com.example.userlookup.UserDB
import com.example.userlookup.lookupfeature.data.model.GetUserPostsRequestParam
import com.example.userlookup.lookupfeature.data.model.GetUsersRequestParam
import com.example.userlookup.lookupfeature.model.User
import com.example.userlookup.lookupfeature.model.UserPost
import com.example.userlookup.lookupfeature.model.toDatalayerObject
import com.example.userlookup.lookupfeature.model.toDomainLayerObject


class UserLookUpLocalDataSource(userDB: UserDB) {
    private var userQueries = userDB.userQueries

    fun getUserFromLocal(userName: String):User {
        return userQueries.GetUserDetailByName(userName).executeAsOne().toDomainLayerObject()
    }

    fun getUserPostsFromLocal(itemId: Long):List<UserPost>
    {
        return userQueries.GetPostByUser(itemId).executeAsList().map { it.toDomainLayerObject() }
    }
    fun insertUserListIntoDB(userList: List<User>)
    {
        userQueries.transaction {
            userList.forEach {
                userQueries.insertorReplaceUser(it.toDatalayerObject())
            }
        }
    }
    fun checkUserExist(userName:String):Boolean
    {
        return userQueries.checkUserExist(userName).executeAsOne()
    }


    fun insertUserPostsListIntoDB(userPostList: List<UserPost>)
    {
        userQueries.transaction {
            userPostList.forEach {
                userQueries.insertorReplacePost(it.toDatalayerObject())
            }
        }
    }

    fun deleteAllUsers()
    {
        return userQueries.deleteAllUsers()
    }
    fun deletePostByUser(userId:Long)
    {
        return userQueries.deleteAllPostByUser(userId)
    }
    companion object
    {
        private var INSTANCE: UserLookUpLocalDataSource? = null
        fun getInstance(userDB: UserDB): UserLookUpLocalDataSource
        {
            if (INSTANCE == null)
            {
                INSTANCE = UserLookUpLocalDataSource(userDB)
            }
            return INSTANCE!!
        }
        fun clearInstance() {
            INSTANCE =null
        }
    }
}