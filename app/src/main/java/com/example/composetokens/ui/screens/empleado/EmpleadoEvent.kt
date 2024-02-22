package com.example.composetokens.ui.screens.empleado

import com.example.composetokens.domain.model.Empleado
import com.serverschema.type.EmpleadoInput

sealed class EmpleadoEvent {
 class GetEmpleados(val id: Long) : EmpleadoEvent()

 class AddEmpleado(val empleadoInput: Empleado) : EmpleadoEvent()
}