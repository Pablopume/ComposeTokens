package com.example.composetokens.ui.screens.updateventa

import com.example.composetokens.domain.model.Venta
sealed class UpdateVentaEvent {
class UpdateVenta (val venta: Venta): UpdateVentaEvent()

}