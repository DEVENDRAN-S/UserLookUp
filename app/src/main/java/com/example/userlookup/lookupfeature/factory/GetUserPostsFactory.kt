package com.example.userlookup.lookupfeature.factory

import com.example.userlookup.common.SqlDelightInitializer
import com.example.userlookup.common.UseCaseFactory
import com.example.userlookup.lookupfeature.data.source.UserLookUpLocalDataSource
import com.example.userlookup.lookupfeature.data.source.UserLookUpRemoteDataSource
import com.example.userlookup.lookupfeature.data.source.UserLookUpRepository
import com.example.userlookup.lookupfeature.domain.GetUserPostsUseCase

object GetUserPostsFactory:UseCaseFactory<GetUserPostsUseCase> {
    override fun createInstance(): GetUserPostsUseCase {
        val repository = UserLookUpRepository(UserLookUpLocalDataSource.getInstance(SqlDelightInitializer.getDatabase()), UserLookUpRemoteDataSource.getInstance())
        return GetUserPostsUseCase(repository)
    }
}