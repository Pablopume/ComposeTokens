package com.example.composetokens.domain.usecases


import com.example.composetokens.data.VentaRepository
import com.example.composetokens.domain.model.Venta

import javax.inject.Inject

class UpdateVentasUseCase @Inject constructor(private val ventaRepository: VentaRepository){
    operator fun invoke(venta: Venta) = ventaRepository.updateVenta(venta)


}