package com.example.composetokens.ui.screens.empleado

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.composetokens.domain.model.Empleado


@Composable
fun EmpleadoScreen(
    viewmodel: EmpleadoViewModel = hiltViewModel(),
    onViewDetalle: (Long?) -> Unit,
    bottomNavigationBar: @Composable () -> Unit = {},
    tiendaId: Long
) {
    val state = viewmodel.state.collectAsStateWithLifecycle()
    ScreenContent(state.value,onViewDetalle,bottomNavigationBar= bottomNavigationBar){
        viewmodel.handleEvent(EmpleadoEvent.GetEmpleados(tiendaId))
    }

    }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreenContent(
    state: EmpleadoState,
    onViewDetalle: (Long?) -> Unit,
    bottomNavigationBar: @Composable () -> Unit,
    function: () -> Unit
) {
function()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold (
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomNavigationBar,
        floatingActionButton = {
            Button(onClick = { /*TODO*/ }) {
                Text("+")
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { innerPadding ->
        LaunchedEffect(state.error) {
            state.error?.let {
                snackbarHostState.showSnackbar(
                    message = state.error.toString(),
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

            items(items = state.lista, key = { persona -> persona.id }) {
                    persona ->
                TiendaItem(persona = persona,
                    onViewDetalle = onViewDetalle,
                    modifier = Modifier.animateItemPlacement(
                        animationSpec = tween(1000)
                    ))
            }
        }

    }

}

@Composable
fun TiendaItem(persona: Empleado,
               onViewDetalle: (Long?) -> Unit,
               modifier: Modifier = Modifier){

    Card(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onViewDetalle(persona.id) } ) {
        Row( modifier = Modifier.padding(8.dp)){
            persona.nombre?.let {
                Text(
                    modifier = Modifier.weight(weight = 0.4F),
                    text = it
                )
            }
            persona.apellido?.let {
                Text(
                    modifier = Modifier.weight(0.4F),
                    text = it
                )
            }

        }
    }

}


