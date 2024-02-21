package com.example.composetokens.ui.screens.venta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetokens.domain.usecases.GetTiendasUseCase
import com.example.composetokens.domain.usecases.GetVentasUseCase

import com.example.plantillaexamen.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VentaViewModel @Inject constructor(private val getVentasUseCase: GetVentasUseCase) :
    ViewModel() {
    private val _state =
        MutableStateFlow(VentaState(lista = emptyList(), loading = false, error = null))
    val state: StateFlow<VentaState> get() = _state



    fun handleEvent(event: VentaEvent) {
        when (event) {
            is VentaEvent.GetVentas -> {
                getTiendas()
            }
        }
    }

    private fun getTiendas() {
        viewModelScope.launch {
            getVentasUseCase().collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        _state.value = _state.value.copy(loading = true)
                    }

                    is NetworkResult.Success -> {
                        result.data?.let { ventas ->
                            _state.value = _state.value.copy(
                                lista = ventas,
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