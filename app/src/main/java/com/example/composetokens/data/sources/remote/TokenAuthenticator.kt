package com.example.composetokens.data.sources.remote


import com.example.composetokens.data.TokenManager
import com.example.composetokens.data.sources.LoginTokens
import com.example.composetokens.data.sources.services.AuthService
import com.example.plantillaexamen.utils.NetworkResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(private val tokenManager: TokenManager) :
    Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        val token = runBlocking {
            tokenManager.getToken().first()
        }
        return if (response.code != 401 || token == null) {
            response.request.newBuilder().build()
        } else {
            runBlocking {
                val newToken = getNewToken(token)
                val newAccessToken = newToken.data?.accessToken
                newAccessToken?.let { tokenManager.saveAccessToken(it) }
                response.request.newBuilder().header("Authorization", "$token").build()
            }
        }

    }

    private fun getNewToken(refreshToken: String?): NetworkResult<LoginTokens> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8764/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthService::class.java)
        return service.refreshToken(refreshToken)
    }
}
