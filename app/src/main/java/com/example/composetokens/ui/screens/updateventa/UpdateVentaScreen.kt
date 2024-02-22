package com.example.composetokens.ui.screens.updateventa


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composetokens.domain.model.Venta



@Composable
fun UpdateVentaScreen(
    viewmodel: UpdateVentaViewModel = hiltViewModel(),
    onUpdated: () -> Unit = {}, ventaId: Long,


    ) {

    val state = viewmodel.state.collectAsStateWithLifecycle()
    ScreenContent(state.value, onUpdated, ventaId) {
        when (it) {
            is UpdateVentaEvent.UpdateVenta -> viewmodel.handleEvent(it)
            is UpdateVentaEvent.SetVenta -> viewmodel.handleEvent(it)
            is UpdateVentaEvent.GetVenta -> viewmodel.handleEvent(it)
        }
    }
}



@Composable
fun ScreenContent(
    state: UpdateVentaState,
    onUpdated: () -> Unit,
    ventaId: Long,

    function: (UpdateVentaEvent) -> Unit
) {
    LaunchedEffect(ventaId) {
        function(UpdateVentaEvent.GetVenta(ventaId))
    }

    val snackbarHostState = remember { SnackbarHostState() }
    var precio by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = { // Add this FloatingActionButton to your Scaffold
            FloatingActionButton(onClick = { function(UpdateVentaEvent.UpdateVenta) }) {
                Text("Update")
            }
        }
    ) { innerPadding ->
        LaunchedEffect(state.error) {
            state.error?.let {
                snackbarHostState.showSnackbar(
                    message = it,
                    actionLabel = "Fuera",
                    duration = SnackbarDuration.Short,
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.Gray)
        ) {
            item {
                TextField(
                    value = precio,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() || char == '.' } && it.count { char -> char == '.' } <= 1) {
                            precio = it
                            state.venta?.let { venta ->
                                Venta(
                                    ventaId,
                                    venta.fecha,
                                    precio.toDoubleOrNull() ?: 0.0,
                                    venta.clienteId,
                                    venta.empleadoId
                                ).let { updatedVenta ->
                                    function(UpdateVentaEvent.SetVenta(venta = updatedVenta))
                                }
                            }
                        }
                    },
                    label = { Text("Precio") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

            }

        }
    }
    LaunchedEffect(state.updated)
    {
        if (state.updated) {
            onUpdated()
        }
    }
}







