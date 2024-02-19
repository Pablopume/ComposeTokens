package com.example.composetokens.ui.screens.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetokens.domain.usecases.GetTiendasUseCase

import com.example.plantillaexamen.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListaViewModel @Inject constructor(private val getTiendasUseCase: GetTiendasUseCase) :
    ViewModel() {
    private val _state =
        MutableStateFlow(ListaState(lista = emptyList(), loading = false, error = ""))
    val state: StateFlow<ListaState> get() = _state

    init {
        getTiendas()
    }

    fun handleEvent(event: ListaEvent) {
        when (event) {
            is ListaEvent.GetTiendas -> {
                getTiendas()
            }
        }
    }

    private fun getTiendas() {
        viewModelScope.launch {
            getTiendasUseCase().collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        _state.value = _state.value.copy(loading = true)
                    }

                    is NetworkResult.Success -> {
                        result.data?.let { tiendas ->
                            _state.value = _state.value.copy(
                                lista = tiendas,
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