package com.example.composetokens.domain.usecases

import com.example.composetokens.CredentialsRegister
import com.example.composetokens.data.LoginRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val loginRepository: LoginRepository) {
     operator fun invoke(credentialsRegister: CredentialsRegister) = loginRepository.register(credentialsRegister)
}