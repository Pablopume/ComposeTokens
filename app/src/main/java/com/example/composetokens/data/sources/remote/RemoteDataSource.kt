package com.example.composetokens.data.sources.remote

import com.example.composetokens.CredentialsRegister
import com.example.composetokens.data.sources.LoginTokens
import com.example.composetokens.data.sources.services.AuthService
import com.example.plantillaexamen.utils.NetworkResult
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val credentialsService: AuthService,
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
                    NetworkResult.Error("${response.code()} ${response.errorBody()}")
                }
            } else {
                if(response.code()==403){
                    NetworkResult.Error("Usuario o contraseña incorrectos")
                }
                else{
                    NetworkResult.Error("${response.code()} ${response.errorBody()}")
                }

            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    fun register(credentialsRegister: CredentialsRegister): NetworkResult<Boolean> {
        return try {
            val call = credentialsService.register(credentialsRegister)
            val response = call.execute()
            if (response.isSuccessful) {
                NetworkResult.Success(true)

            } else {
                if (response.code() == 409) {
                    NetworkResult.Error("El usuario ya existe")
                } else {
                    NetworkResult.Error("${response.code()} ${response.errorBody()}")
                }
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }
}
