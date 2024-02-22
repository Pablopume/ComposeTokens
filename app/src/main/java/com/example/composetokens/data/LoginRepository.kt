package com.example.composetokens.data

import com.example.composetokens.CredentialsRegister
import com.example.composetokens.data.sources.LoginTokens
import com.example.composetokens.data.sources.remote.RemoteDataSource
import com.example.plantillaexamen.utils.NetworkResult
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@ActivityRetainedScoped
class LoginRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val dispatcher: CoroutineDispatcher
) {
    fun login(user: String, password: String): Flow<NetworkResult<LoginTokens>> {
        return flow {
            val response = remoteDataSource.login(user, password)
            emit(response)
            emit(NetworkResult.Loading())

        }.flowOn(dispatcher)
    }

    fun register(credentialsRegister: CredentialsRegister): Flow<NetworkResult<Boolean>> {
        return flow {
            val response = remoteDataSource.register(credentialsRegister)
            emit(response)
            emit(NetworkResult.Loading())

        }.flowOn(dispatcher)
    }
}