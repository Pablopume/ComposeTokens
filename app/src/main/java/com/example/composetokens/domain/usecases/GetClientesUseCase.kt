package com.example.composetokens.domain.usecases

import com.example.composetokens.data.ClienteRepository

import javax.inject.Inject

class GetClientesUseCase @Inject constructor(private val clienteRepository: ClienteRepository){
    operator fun invoke() = clienteRepository.getClientes()


}