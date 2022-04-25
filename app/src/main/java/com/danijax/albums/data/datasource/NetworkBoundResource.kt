package com.danijax.albums.data.datasource

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.util.*
//Provides strategy to handle offline first
abstract class NetworkBoundResource<ResultType, RequestType>{

    private val TAG = "NetworkBoundResource"
    fun asFlow() = flow {
       // emit(Resource.Loading(null))

        if(isCacheResult()){
            emitAll(loadFromCache().map { Resource.Success(it) })
        }

        else {
            val dbResult = loadFromDb().first()
            saveToCache(dbResult)
            emit(Resource.Loading(dbResult))
            if (shouldFetch(convert(dbResult))){
                emit(Resource.Loading(dbResult))
                try {
                    val flowResult = fetchFromNetwork().first()
                    saveNetworkResult(flowResult)
                    emitAll(loadFromDb().map { Resource.Success(it) })
                }
                catch (ex: Exception){
                    emit(Resource.Error(ex.localizedMessage, null))
                }
            }
            else{
                emitAll(loadFromDb().map { Resource.Success(it) })
            }
        }
    }

    @WorkerThread
    abstract fun saveToCache(resultType: ResultType)

    @WorkerThread
    abstract fun isCacheResult(): Boolean

    @WorkerThread
    abstract fun loadFromCache():Flow<ResultType>

    @WorkerThread
    abstract fun loadFromDb(): Flow<ResultType>

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