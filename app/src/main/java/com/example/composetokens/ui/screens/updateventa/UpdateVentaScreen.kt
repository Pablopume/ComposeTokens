package com.example.composetokens.ui.screens.updateventa


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composetokens.domain.model.Venta

import com.example.composetokens.ui.screens.venta.VentaState
import com.example.composetokens.ui.screens.venta.VentaViewModel
import java.time.LocalDate


@Composable
fun UpdateVentaScreen(viewmodel: UpdateVentaViewModel = hiltViewModel()) {
val venta: Venta= Venta(0,"",20.0)
    val state = viewmodel.state.collectAsStateWithLifecycle()
    ScreenContent(state.value){viewmodel.handleEvent(UpdateVentaEvent.UpdateVenta(venta))}
}


@Composable
fun ScreenContent(state: UpdateVentaState, function: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    var precio by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = { // Add this FloatingActionButton to your Scaffold
            FloatingActionButton(onClick = { function }) {
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
                    onValueChange = { nuevoPrecio -> precio = nuevoPrecio },
                    label = { Text("Precio") }
                )
            }
        }
    }
}





