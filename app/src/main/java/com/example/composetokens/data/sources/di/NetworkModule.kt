package com.example.composetokens.data.sources.di
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.example.composetokens.data.sources.remote.ServiceInterceptor
import com.example.composetokens.data.sources.remote.TokenAuthenticator
import com.example.composetokens.data.sources.services.AuthService
import com.example.composetokens.data.sources.services.CredentialsService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .build()
    }





    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.2.3.112:8764/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }


    @Singleton
    @Provides
    fun provideCredentialsService(retrofit: Retrofit): CredentialsService =
        retrofit.create(CredentialsService::class.java)
    @Singleton
    @Provides
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")


    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
    @Singleton
    @Provides
    fun provideConverterMoshiFactory(): MoshiConverterFactory {
        val moshi = Moshi.Builder()
            .build()
        return MoshiConverterFactory.create(moshi)
    }

    @Singleton
    @Provides
    fun createApolloClient(interceptor: ServiceInterceptor, authenticator: TokenAuthenticator): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("http://192.168.1.155:8765/graphql")
            .okHttpClient(
                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .authenticator(authenticator)
                    .build()
            )
            .build()
    }
}