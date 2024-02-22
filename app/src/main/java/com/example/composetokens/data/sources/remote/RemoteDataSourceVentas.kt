package com.example.composetokens.data.sources.remote

import com.apollographql.apollo3.ApolloClient
import com.example.composetokens.data.toUpdateVentaInput
import com.example.composetokens.data.toVenta
import com.example.composetokens.domain.model.Tienda
import com.example.composetokens.domain.model.Venta

import com.example.plantillaexamen.utils.NetworkResult
import com.serverschema.GetVentaByIdQuery
import com.serverschema.GetVentasQuery
import com.serverschema.UpdateVentaMutation
import com.serverschema.type.UpdateVentaInput

import javax.inject.Inject


class RemoteDataSourceVentas @Inject constructor(private val apolloClient: ApolloClient) {

    suspend fun getVentas(): NetworkResult<List<Venta>> {
        return try {
            val response = apolloClient.query(GetVentasQuery()).execute()
            if (response.hasErrors()) {
                NetworkResult.Error(response.errors?.first()?.message ?: "Error Desconocido")

            } else {
                val ventas = response.data?.getVentas?.map {
                    it.toVenta()
                }
                if (ventas != null) {
                    NetworkResult.Success(ventas)
                } else {
                    NetworkResult.Error(response.errors?.first()?.message ?: "Error Desconocido")
                }
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }

    }

    suspend fun updateVenta(venta: Venta): NetworkResult<String> {
        return try {
            val updateVentaInput: UpdateVentaInput = venta.toUpdateVentaInput(venta)
            val response = apolloClient.mutation(UpdateVentaMutation(updateVentaInput)).execute()
            if (response.hasErrors()) {
                NetworkResult.Error(response.errors?.first()?.message ?: "Error Desconocido")
            } else {
                NetworkResult.Success("Venta actualizada")
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }


    }

    suspend fun getVentaById(id: Long): NetworkResult<Venta> {
        return try {
            val response = apolloClient.query(GetVentaByIdQuery(id.toString())).execute()
            if (response.hasErrors()) {
                NetworkResult.Error(response.errors?.first()?.message ?: "Error Desconocido")
            } else {
                val venta = response.data?.getVentaById?.toVenta()
                if (venta != null) {
                    NetworkResult.Success(venta)
                } else {
                    NetworkResult.Error(response.errors?.first()?.message ?: "Error Desconocido")
                }
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }
}
