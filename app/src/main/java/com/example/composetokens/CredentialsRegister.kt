package com.example.composetokens

import com.google.gson.annotations.SerializedName

data class CredentialsRegister (
    @SerializedName("username")
    var username: String? = null,
    @SerializedName("password")
    var password: String? = null

)