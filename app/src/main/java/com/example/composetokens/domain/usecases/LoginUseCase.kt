package com.example.composetokens.domain.usecases

import com.example.composetokens.data.LoginRepository
import com.example.composetokens.data.TokenManager
import com.example.composetokens.data.sources.LoginTokens
import com.example.plantillaexamen.utils.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository,private val tokenManager: TokenManager){
    suspend operator fun invoke(email: String, password: String): Flow<NetworkResult<LoginTokens>> {
      val resultado=  loginRepository.login(email, password)
        resultado.collect{result->
            if(result is NetworkResult.Success){
                result.data?.accessToken?.let { tokenManager.saveAccessToken(it) }
                result.data?.refreshToken?.let { tokenManager.saveToken(it) }
            }
        }
        return resultado
    }
}