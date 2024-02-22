package com.example.composetokens.ui.screens.login

import com.example.composetokens.CredentialsRegister

sealed class LoginEvent {
    data object Login : LoginEvent()
    class Register(var register: CredentialsRegister) : LoginEvent()

}