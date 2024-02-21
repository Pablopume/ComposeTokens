package com.example.composetokens.ui.screens.empleado

sealed class EmpleadoEvent {
 class GetEmpleados(val id: Long) : EmpleadoEvent()

}