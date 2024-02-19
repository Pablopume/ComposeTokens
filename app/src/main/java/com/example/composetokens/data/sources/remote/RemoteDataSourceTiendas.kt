package com.example.composetokens.data.sources.remote

import com.apollographql.apollo3.ApolloClient
import com.example.composetokens.data.toTiendas
import com.example.composetokens.domain.model.Tienda
import com.example.plantillaexamen.utils.NetworkResult
import com.serverschema.GetTiendasQuery
import javax.inject.Inject


class RemoteDataSourceTiendas @Inject constructor( private val apolloClient: ApolloClient)
 {

   suspend fun getTiendas(): NetworkResult<List<Tienda>> {
        return try {
            val response = apolloClient.query(GetTiendasQuery()).execute()
            if (response.hasErrors()) {
                    NetworkResult.Error("${response.errors?.first()?.message ?: ""}")
            } else {
                val tiendas = response.data?.getTiendas?.map {
                    it.toTiendas()
                }
                if (tiendas != null) {
                    NetworkResult.Success(tiendas)
                } else {
                    NetworkResult.Error("${response.errors?.first()?.message ?: ""}")
                }
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }

    }

    
}
