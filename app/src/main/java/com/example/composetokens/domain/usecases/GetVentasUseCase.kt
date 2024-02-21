package com.example.composetokens.domain.usecases

import com.example.composetokens.data.ClienteRepository
import com.example.composetokens.data.VentaRepository

import javax.inject.Inject

class GetVentasUseCase @Inject constructor(private val ventaRepository: VentaRepository){
    operator fun invoke() = ventaRepository.getVentas()


}