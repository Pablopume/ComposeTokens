package com.example.composetokens.data.sources.services

import com.example.composetokens.data.sources.LoginTokens
import retrofit2.Call


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CredentialsService {

    @GET("/login")
    fun getLogin(@Query("user") user: String, @Query("password") password: String): Call<LoginTokens>


}