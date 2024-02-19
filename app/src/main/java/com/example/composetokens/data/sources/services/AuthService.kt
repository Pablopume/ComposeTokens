package com.example.composetokens.data.sources.services

import com.example.composetokens.data.sources.LoginTokens
import com.example.plantillaexamen.utils.NetworkResult
import retrofit2.http.GET
import retrofit2.http.Query


interface AuthService {

    @GET("refreshToken")
    fun refreshToken(@Query("refreshToken") refreshToken: String?): NetworkResult<LoginTokens>


}