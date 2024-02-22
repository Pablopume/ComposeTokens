package com.example.composetokens.data.sources.remote

import com.apollographql.apollo3.ApolloClient
import com.example.composetokens.data.toCliente
import com.example.composetokens.data.toEmpleado
import com.example.composetokens.data.toTiendas
import com.example.composetokens.domain.model.Cliente
import com.example.composetokens.domain.model.Empleado
import com.example.composetokens.domain.model.Tienda
import com.example.plantillaexamen.utils.NetworkResult
import com.serverschema.DeleteClienteMutation
import com.serverschema.GetClienteQuery
import com.serverschema.GetEmpleadosByIdTiendaQuery
import com.serverschema.GetTiendasQuery
import javax.inject.Inject


class RemoteDataSourceCliente @Inject constructor(private val apolloClient: ApolloClient) {

    suspend fun getClientes(): NetworkResult<List<Cliente>> {
        return try {
            val response = apolloClient.query(GetClienteQuery()).execute()
            if (response.hasErrors()) {
                NetworkResult.Error(response.errors?.first()?.message ?: "Error Desconocido")

            } else {
                val clientes = response.data?.getClientes?.map {
                    it.toCliente()
                }
                if (clientes != null) {
                    NetworkResult.Success(clientes)
                } else {
                    NetworkResult.Error(response.errors?.first()?.message ?: "Error Desconocido")
                }
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }

    }

    suspend fun deleteCliente(id: Long): NetworkResult<String> {
        return try {
            val response = apolloClient.mutation(DeleteClienteMutation(id.toString())).execute()
            if (response.hasErrors()) {
                NetworkResult.Error(response.errors?.first()?.message ?: "Error Desconocido")
            } else {
                NetworkResult.Success("Cliente eliminado")
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

}
