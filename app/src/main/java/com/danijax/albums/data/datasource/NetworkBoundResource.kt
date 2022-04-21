package com.danijax.albums.data.datasource

import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.util.*

abstract class NetworkBoundResource<ResultType, RequestType>{

    fun asFlow() = flow {
       // emit(Resource.Loading(null))
        val dbResult = loadFromDb().first()
        if (shouldFetch(convert(dbResult))){
            emit(Resource.Loading(dbResult))

            fetchFromNetwork()
                .onEach {
                    saveNetworkResult(it)
                    emitAll(loadFromDb().map { map-> Resource.Success(map) })
                }
                .catch { k ->
//                    emit(Resource.Error( k.localizedMessage, null))
                }
                .flowOn(Dispatchers.IO)
                .collect()
        }
        else{
            emitAll(loadFromDb().map { Resource.Success(it) })
        }
    }


    @WorkerThread
    abstract fun loadFromDb(): Flow<ResultType>

//    @WorkerThread
//    abstract fun getDataFetchDate(data: ResultType): Date?
//
    @WorkerThread
    abstract fun shouldFetch(data: RequestType): Boolean

    @WorkerThread
    abstract fun convert(data: ResultType): RequestType

    @WorkerThread
    protected abstract suspend fun saveNetworkResult(item: RequestType)
//
    @WorkerThread
    abstract fun fetchFromNetwork():Flow<RequestType>
}