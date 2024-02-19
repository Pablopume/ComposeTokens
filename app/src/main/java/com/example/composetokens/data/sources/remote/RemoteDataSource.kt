package com.example.composetokens.data.sources.remote

import com.example.composetokens.data.TokenManager
import com.example.composetokens.data.sources.LoginTokens
import com.example.composetokens.data.sources.services.AuthService
import com.example.composetokens.data.sources.services.CredentialsService
import com.example.plantillaexamen.utils.NetworkResult
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val credentialsService: CredentialsService,
) {
    fun login(email: String, password: String): NetworkResult<LoginTokens> {
        return try {
            val call = credentialsService.getLogin(email, password)
            val response = call.execute()
            if (response.isSuccessful) {
                val loginTokens = response.body()
                if (loginTokens != null) {
                    NetworkResult.Success(loginTokens)
                } else {
                    NetworkResult.Error("${response.code()} ${response.message()}")
                }

            } else {
                NetworkResult.Error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }
}
