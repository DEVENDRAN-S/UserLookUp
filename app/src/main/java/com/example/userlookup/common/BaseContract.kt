package com.example.userlookup.common

interface UsecaseContract<Request, CALLBACK> {
    suspend fun  execute(request: Request, callback: CALLBACK)
}
interface UseCaseFactory<T> {
    fun createInstance(): T
}
