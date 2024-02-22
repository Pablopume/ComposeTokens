package com.example.composetokens.ui.screens.producto

import com.example.composetokens.domain.model.Producto


data class ProductoState(
    val lista: List<Producto> = emptyList(), val loading: Boolean = false, val error: String? = null

)