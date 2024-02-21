package com.example.composetokens.ui.screens.venta

import com.example.composetokens.domain.model.Venta

data class VentaState(
    val lista: List<Venta> = emptyList(), val loading: Boolean = false, val error: String? = null

)