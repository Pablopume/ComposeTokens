package com.example.composetokens.data

import com.example.composetokens.data.sources.remote.RemoteDataSourceProductos
import com.example.composetokens.domain.model.Producto
import com.example.composetokens.domain.model.Tienda
import com.example.plantillaexamen.utils.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProductoRepository @Inject constructor(
    private val remoteDataSourceProductos: RemoteDataSourceProductos,
    private val dispatcher: CoroutineDispatcher
) {
    fun getProductos(): Flow<NetworkResult<List<Producto>>> {
        return flow {
            emit(NetworkResult.Loading())
            val response = remoteDataSourceProductos.getProductos()
            emit(response)
        }.flowOn(dispatcher)
    }
}
