package com.example.composetokens.ui.screens.lista

import com.example.composetokens.domain.model.Tienda

data class ListaState(
    val lista: List<Tienda> = emptyList(), val loading: Boolean = false, val error: String = ""

)