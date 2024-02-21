package com.example.composetokens.domain.usecases

import com.example.composetokens.data.ClienteRepository

import javax.inject.Inject

class DeleteClienteUseCase @Inject constructor(private val clienteRepository: ClienteRepository){
    operator fun invoke(id:Long) = clienteRepository.deleteCliente(id)


}