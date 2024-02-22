package com.example.composetokens.domain.usecases

import com.example.composetokens.data.ClienteRepository
import com.example.composetokens.data.VentaRepository

import javax.inject.Inject

class GetVentaByIdUseCase @Inject constructor(private val ventaRepository: VentaRepository){
    operator fun invoke(id:Long) = ventaRepository.getVentaById(id)


}