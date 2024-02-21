package com.example.composetokens.ui.screens.empleado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetokens.domain.usecases.GetEmpleadosByIdUseCase
import com.example.composetokens.domain.usecases.GetTiendasUseCase

import com.example.plantillaexamen.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmpleadoViewModel @Inject constructor(private val getEmpleadosByIdUseCase: GetEmpleadosByIdUseCase) :
    ViewModel() {
    private val _state =
        MutableStateFlow(EmpleadoState(lista = emptyList(), loading = false, error = null))
    val state: StateFlow<EmpleadoState> get() = _state


    fun handleEvent(event: EmpleadoEvent) {
        when (event) {
            is EmpleadoEvent.GetEmpleados -> {
                getEmpleados(event.id)
            }
        }
    }

    private fun getEmpleados(id: Long) {
        viewModelScope.launch {
            getEmpleadosByIdUseCase(id).collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        _state.value = _state.value.copy(loading = true)
                    }

                    is NetworkResult.Success -> {
                        result.data?.let { empleados ->
                            _state.value = _state.value.copy(
                                lista = empleados,
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