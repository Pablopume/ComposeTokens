package com.example.composetokens.ui.screens.empleado

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetokens.domain.model.Empleado
import com.example.composetokens.domain.usecases.AddEmpleadoUseCase
import com.example.composetokens.domain.usecases.GetEmpleadosByIdUseCase
import com.example.composetokens.domain.usecases.GetTiendasUseCase

import com.example.plantillaexamen.utils.NetworkResult
import com.serverschema.type.EmpleadoInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmpleadoViewModel @Inject constructor(private val getEmpleadosByIdUseCase: GetEmpleadosByIdUseCase, private val addEmpleadoUseCase: AddEmpleadoUseCase) :
    ViewModel() {
    private val _state =
        MutableStateFlow(EmpleadoState(lista = emptyList(), loading = false, error = null))
    val state: StateFlow<EmpleadoState> get() = _state


    fun handleEvent(event: EmpleadoEvent) {
        when (event) {
            is EmpleadoEvent.GetEmpleados -> {
                getEmpleados(event.id)
            }
            is EmpleadoEvent.AddEmpleado -> {
                addEmpleado(event.empleadoInput)
            }
        }
    }

    private fun addEmpleado(empleadoInput: Empleado) {

        viewModelScope.launch {
            addEmpleadoUseCase(empleadoInput).collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        _state.value = _state.value.copy(loading = true)
                    }

                    is NetworkResult.Success -> {
                        _state.value = _state.value.copy(
                            loading = false, lista = _state.value.lista.plus(empleadoInput)
                        )
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