package com.example.composetokens.data.sources.services

import com.example.composetokens.CredentialsRegister
import com.example.composetokens.data.sources.LoginTokens
import com.example.plantillaexamen.utils.NetworkResult
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface AuthService {
    @GET("/login")
    fun getLogin(@Query("user") user: String, @Query("password") password: String): Call<LoginTokens>
    @GET("refreshToken")
    fun refreshToken(@Query("refreshToken") refreshToken: String?): Call<LoginTokens>

    @POST("credentials")
    fun register(@Body credential: CredentialsRegister): Call<Boolean>


}