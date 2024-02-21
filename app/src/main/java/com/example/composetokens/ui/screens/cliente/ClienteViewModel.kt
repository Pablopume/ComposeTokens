package com.example.composetokens.ui.screens.cliente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetokens.domain.usecases.DeleteClienteUseCase
import com.example.composetokens.domain.usecases.GetClientesUseCase

import com.example.plantillaexamen.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClienteViewModel @Inject constructor(private val getClienteUseCase: GetClientesUseCase,private val deleteClienteUseCase: DeleteClienteUseCase) :
    ViewModel() {
    private val _state =
        MutableStateFlow(ClienteState(lista = emptyList(), loading = false, error = null))
    val state: StateFlow<ClienteState> get() = _state


    fun handleEvent(event: ClienteEvent) {
        when (event) {
            is ClienteEvent.GetClientes -> {
                getClientes()
            }
            is ClienteEvent.DeleteCliente -> {
                deleteCliente(event.id)
            }
        }
    }

    private fun deleteCliente(id: Long) {
viewModelScope.launch {
    deleteClienteUseCase(id).collect { result ->
        when (result) {
            is NetworkResult.Loading -> {
                _state.value = _state.value.copy(loading = true)
            }
            is NetworkResult.Success -> {
                getClientes()
            }
            is NetworkResult.Error -> {
                _state.value = _state.value.copy(error = result.message ?: "Error")
            }
        }
    }
}
    }

    private fun getClientes() {
        viewModelScope.launch {
            getClienteUseCase().collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        _state.value = _state.value.copy(loading = true)
                    }

                    is NetworkResult.Success -> {
                        result.data?.let { clientes ->
                            _state.value = _state.value.copy(
                                lista = clientes,
                                loading = false
                            )
                        }
                    }

                    is NetworkResult.Error -> {
                        _state.value = _state.value.copy(
                            error = result.message ?: "Error",
                            loading = false
                        )
                    }
                }
            }
        }

    }
}