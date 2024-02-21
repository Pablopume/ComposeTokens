package com.example.composetokens.data.sources.remote

import com.apollographql.apollo3.ApolloClient
import com.example.composetokens.data.toEmpleado
import com.example.composetokens.data.toTiendas
import com.example.composetokens.domain.model.Empleado
import com.example.composetokens.domain.model.Tienda
import com.example.plantillaexamen.utils.NetworkResult
import com.serverschema.GetEmpleadosByIdTiendaQuery
import com.serverschema.GetTiendasQuery
import javax.inject.Inject


class RemoteDataSourceEmpleado @Inject constructor(private val apolloClient: ApolloClient)
 {

   suspend fun getEmpleadosById(id: Long): NetworkResult<List<Empleado>> {
        return try {
            val response = apolloClient.query(GetEmpleadosByIdTiendaQuery(id.toString())).execute()
            if (response.hasErrors()) {
                    NetworkResult.Error(response.errors?.first()?.message ?: "Error Desconocido")

            } else {
                val empleados = response.data?.getEmpleadosByIdTienda?.map {
                    it.toEmpleado()
                }
                if (empleados != null) {
                    NetworkResult.Success(empleados)
                } else {
                    NetworkResult.Error(response.errors?.first()?.message ?: "Error Desconocido")
                }
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }

    }

    
}
