package com.example.composetokens.data

import com.example.composetokens.data.sources.remote.RemoteDataSourceTiendas
import com.example.composetokens.domain.model.Tienda
import com.example.plantillaexamen.utils.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class TiendaRepository @Inject constructor(private val remoteDataSourceTiendas: RemoteDataSourceTiendas, private val dispatcher: CoroutineDispatcher = Dispatchers.IO){
    fun getTiendas(): Flow<NetworkResult<List<Tienda>>> {
        return flow {
            val response = remoteDataSourceTiendas.getTiendas()
            emit(response)
            emit(NetworkResult.Loading())
        }.flowOn(dispatcher)


    }
}