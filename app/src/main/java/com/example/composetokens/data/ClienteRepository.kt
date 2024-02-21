package com.example.composetokens.data

import com.example.composetokens.data.sources.remote.RemoteDataSourceCliente
import com.example.composetokens.domain.model.Cliente
import com.example.plantillaexamen.utils.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class ClienteRepository @Inject constructor(private val remoteDataSourceClientes: RemoteDataSourceCliente, private val dispatcher: CoroutineDispatcher ){
    fun getClientes(): Flow<NetworkResult<List<Cliente>>> {
        return flow {
            emit(NetworkResult.Loading())
            val response = remoteDataSourceClientes.getClientes()
            emit(response)
        }.flowOn(dispatcher)
    }
    fun deleteCliente(id: Long): Flow<NetworkResult<String>> {
        return flow {
            emit(NetworkResult.Loading())
            val response = remoteDataSourceClientes.deleteCliente(id)
            emit(response)
        }.flowOn(dispatcher)
    }

}