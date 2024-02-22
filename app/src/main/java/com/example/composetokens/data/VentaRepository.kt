package com.example.composetokens.data

import com.example.composetokens.data.sources.remote.RemoteDataSourceEmpleado
import com.example.composetokens.data.sources.remote.RemoteDataSourceVentas
import com.example.composetokens.domain.model.Empleado
import com.example.composetokens.domain.model.Venta
import com.example.plantillaexamen.utils.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class VentaRepository @Inject constructor(private val remoteDataSource: RemoteDataSourceVentas, private val dispatcher: CoroutineDispatcher ){
    fun getVentas() : Flow<NetworkResult<List<Venta>>> {

        return flow {
            emit(NetworkResult.Loading())
            val response = remoteDataSource.getVentas()
            emit(response)
        }.flowOn(dispatcher)
    }
    fun updateVenta(venta: Venta) : Flow<NetworkResult<String>> {
        return flow {
            emit(NetworkResult.Loading())
            val response = remoteDataSource.updateVenta(venta)
            emit(response)
        }.flowOn(dispatcher)
    }

    fun getVentaById(id: Long) : Flow<NetworkResult<Venta>> {
        return flow {
            emit(NetworkResult.Loading())
            val response = remoteDataSource.getVentaById(id)
            emit(response)
        }.flowOn(dispatcher)
    }
}