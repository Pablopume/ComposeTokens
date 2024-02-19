package com.example.composetokens.ui.screens.login

data class PantallaLoginState (val logged: Boolean = false, val loading: Boolean = false, val error: String = "", var username: String="", var password: String=""
)