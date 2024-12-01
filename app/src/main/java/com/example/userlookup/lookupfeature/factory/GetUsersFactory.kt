package com.example.userlookup.lookupfeature.factory

import com.example.userlookup.common.SqlDelightInitializer
import com.example.userlookup.common.UseCaseFactory
import com.example.userlookup.lookupfeature.data.source.UserLookUpLocalDataSource
import com.example.userlookup.lookupfeature.data.source.UserLookUpRemoteDataSource
import com.example.userlookup.lookupfeature.data.source.UserLookUpRepository
import com.example.userlookup.lookupfeature.domain.GetUsersUseCase

object GetUsersFactory:UseCaseFactory<GetUsersUseCase> {
    override fun createInstance(): GetUsersUseCase {
        val repository = UserLookUpRepository(UserLookUpLocalDataSource.getInstance(SqlDelightInitializer.getDatabase()), UserLookUpRemoteDataSource.getInstance())
        return GetUsersUseCase(repository)
    }
}