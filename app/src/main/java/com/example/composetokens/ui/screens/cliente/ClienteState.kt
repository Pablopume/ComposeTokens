package com.example.composetokens.ui.screens.cliente

import com.example.composetokens.domain.model.Cliente
import com.example.composetokens.domain.model.Empleado

data class ClienteState(
    val lista: List<Cliente> = emptyList(), val loading: Boolean = false, val error: String? = ""

)