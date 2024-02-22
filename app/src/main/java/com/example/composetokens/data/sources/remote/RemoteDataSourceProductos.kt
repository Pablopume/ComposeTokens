package com.example.composetokens.data.sources.remote

import com.apollographql.apollo3.ApolloClient
import com.example.composetokens.data.toProducto
import com.example.composetokens.domain.model.Producto
import com.example.plantillaexamen.utils.NetworkResult
import com.serverschema.GetProductosQuery
import javax.inject.Inject


class RemoteDataSourceProductos @Inject constructor(private val apolloClient: ApolloClient){
    suspend fun getProductos(): NetworkResult<List<Producto>> {
        return try {
            val response = apolloClient.query(GetProductosQuery()).execute()
            if (response.hasErrors()) {
                NetworkResult.Error(response.errors?.first()?.message ?: "Error Desconocido")

            } else {
                val productos = response.data?.getProductos?.map {
                    it.toProducto()
                }
                if (productos != null) {
                    NetworkResult.Success(productos)
                } else {
                    NetworkResult.Error(response.errors?.first()?.message ?: "Error Desconocido")
                }
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }

    }
}