package com.example.composetokens.ui.screens.updateventa

import com.example.composetokens.domain.model.Venta
sealed class UpdateVentaEvent {
    data object UpdateVenta : UpdateVentaEvent()
    data class SetVenta(val venta: Venta) : UpdateVentaEvent()
    data class GetVenta(val id: Long) : UpdateVentaEvent()

}