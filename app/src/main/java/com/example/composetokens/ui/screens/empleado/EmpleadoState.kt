package com.example.composetokens.ui.screens.empleado

import com.example.composetokens.domain.model.Empleado
import com.example.composetokens.domain.model.Tienda

data class EmpleadoState(
    val lista: List<Empleado> = emptyList(), val loading: Boolean = false, val error: String? = ""

)