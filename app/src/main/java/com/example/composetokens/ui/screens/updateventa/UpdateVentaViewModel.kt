package com.example.composetokens.ui.screens.updateventa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetokens.domain.model.Venta
import com.example.composetokens.domain.usecases.GetTiendasUseCase
import com.example.composetokens.domain.usecases.GetVentaByIdUseCase
import com.example.composetokens.domain.usecases.GetVentasUseCase
import com.example.composetokens.domain.usecases.UpdateVentasUseCase

import com.example.plantillaexamen.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateVentaViewModel @Inject constructor(
    private val updateVentaUseCase: UpdateVentasUseCase,
    private val getVentaByIdUseCase: GetVentaByIdUseCase
) :
    ViewModel() {
    private val _state =
        MutableStateFlow(UpdateVentaState(updated = false, loading = false, error = null))
    val state: StateFlow<UpdateVentaState> get() = _state


    fun handleEvent(event: UpdateVentaEvent) {
        when (event) {
            is UpdateVentaEvent.UpdateVenta -> {
                updateVentas()
            }

            is UpdateVentaEvent.SetVenta -> {
                setVenta(event.venta)
            }
            is UpdateVentaEvent.GetVenta -> {
                getVenta(event.id)
            }
        }
    }

    private fun getVenta(id: Long) {
        viewModelScope.launch {
            getVentaByIdUseCase(id).collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        _state.value = _state.value.copy(loading = true)
                    }

                    is NetworkResult.Success -> {
                        result.data?.let {
                            _state.value = _state.value.copy(
                                venta = it,
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

    private fun updateVentas() {
        viewModelScope.launch {
            _state.value.venta?.let {
                updateVentaUseCase(it).collect { result ->
                    when (result) {
                        is NetworkResult.Loading -> {
                            _state.value = _state.value.copy(loading = true)
                        }

                        is NetworkResult.Success -> {
                            result.data?.let {
                                _state.value = _state.value.copy(
                                    updated = true,
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

    private fun setVenta(venta: Venta) {
        _state.value = _state.value.copy(venta = venta)
    }
}