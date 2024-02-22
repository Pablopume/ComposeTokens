package com.example.composetokens.ui.screens.producto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetokens.domain.usecases.GetProductosUseCase
import com.example.composetokens.domain.usecases.GetVentasUseCase

import com.example.plantillaexamen.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductoViewModel @Inject constructor(private val getProductosUseCase: GetProductosUseCase) :
    ViewModel() {
    private val _state =
        MutableStateFlow(ProductoState(lista = emptyList(), loading = false, error = null))
    val state: StateFlow<ProductoState> get() = _state



    fun handleEvent(event: ProductoEvent) {
        when (event) {
            is ProductoEvent.GetProductos -> {
                getProductos()
            }
        }
    }

    private fun getProductos() {
        viewModelScope.launch {
            getProductosUseCase().collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        _state.value = _state.value.copy(loading = true)
                    }

                    is NetworkResult.Success -> {
                        result.data?.let { productos ->
                            _state.value = _state.value.copy(
                                lista = productos,
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