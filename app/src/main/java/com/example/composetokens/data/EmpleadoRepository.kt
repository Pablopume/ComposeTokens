package com.example.composetokens.data

import com.example.composetokens.data.sources.remote.RemoteDataSourceEmpleado
import com.example.composetokens.domain.model.Empleado
import com.example.plantillaexamen.utils.NetworkResult
import com.serverschema.type.EmpleadoInput
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EmpleadoRepository @Inject constructor(private val remoteDataSource: RemoteDataSourceEmpleado, private val dispatcher: CoroutineDispatcher ){
    fun getEmpleadosById(id: Long) : Flow<NetworkResult<List<Empleado>>> {

        return flow {
            emit(NetworkResult.Loading())
            val response = remoteDataSource.getEmpleadosById(id)
            emit(response)
        }.flowOn(dispatcher)
    }
    fun addEmpleado(empleado: Empleado) : Flow<NetworkResult<String>> {
        return flow {
            emit(NetworkResult.Loading())
            val response = remoteDataSource.addEmpleado(empleado)
            emit(response)
        }.flowOn(dispatcher)
    }
}