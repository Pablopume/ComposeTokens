package com.example.composetokens.data.sources

import com.google.gson.annotations.SerializedName

data class LoginTokens(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("refreshToken")
    val refreshToken: String
)