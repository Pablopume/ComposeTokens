package com.example.composetokens.ui.screens.cliente

sealed class ClienteEvent {
 data object GetClientes : ClienteEvent()

 class DeleteCliente(val id: Long) : ClienteEvent()

}