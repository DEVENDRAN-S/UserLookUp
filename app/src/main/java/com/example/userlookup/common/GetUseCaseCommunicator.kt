package com.example.userlookup.common

interface GetUseCaseCommunicator<REQUEST, DATA> {
     fun serverCallInitiated(request: REQUEST)
     fun errorOccurred(request: REQUEST, error: String)
     fun noDataFound(request: REQUEST)
     fun gotData(request: REQUEST, data: DATA)
}