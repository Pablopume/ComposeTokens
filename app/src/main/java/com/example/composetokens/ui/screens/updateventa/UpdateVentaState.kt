package com.example.composetokens.ui.screens.updateventa

import com.example.composetokens.domain.model.Venta

data class UpdateVentaState(
    val updated:Boolean= false, val loading: Boolean=false ,val error: String? = null, val venta: Venta? = null

)