package com.example.composetokens.ui.screens.empleado

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composetokens.domain.model.Empleado
import com.example.composetokens.ui.screens.updateventa.UpdateVentaEvent


@Composable
fun EmpleadoScreen(
    viewmodel: EmpleadoViewModel = hiltViewModel(),

    bottomNavigationBar: @Composable () -> Unit = {},
    tiendaId: Long
) {
    val state = viewmodel.state.collectAsStateWithLifecycle()
    ScreenContent(state.value,bottomNavigationBar= bottomNavigationBar,tiendaId)
        {
            when (it) {
                is EmpleadoEvent.AddEmpleado -> viewmodel.handleEvent(it)
                is EmpleadoEvent.GetEmpleados -> viewmodel.handleEvent(it)

            }
        }
    }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreenContent(
    state: EmpleadoState,

    bottomNavigationBar: @Composable () -> Unit,
    tiendaId: Long,
    function: (EmpleadoEvent) -> Unit

) {
    LaunchedEffect(tiendaId) {
        function(EmpleadoEvent.GetEmpleados(tiendaId))
    }

    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomNavigationBar,
        floatingActionButton = {
            FloatingActionButton(

                onClick = {

                    function(
                        EmpleadoEvent.AddEmpleado(
                            Empleado(
                                0,
                                "Paco",
                                "Jimenez",
                                "Jefazo",
                                tiendaId
                            )
                        )
                    )
                },
            ) {
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
            items(
                items = state.lista
            ) { empleado ->
                var isVisible by remember { mutableStateOf(false) }

                LaunchedEffect(key1 = empleado) {
                    isVisible = true
                }

                AnimatedVisibility(
                    visible = isVisible,
                    enter = slideInVertically(initialOffsetY = { -it }),
                ) {
                    TiendaItem(
                        empleado = empleado,
                        modifier = Modifier.animateItemPlacement(
                            animationSpec = tween(1000)
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun TiendaItem(empleado: Empleado,

               modifier: Modifier = Modifier){

    Card(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)
 ) {
        Row( modifier = Modifier.padding(8.dp)){
            Text(
                modifier = Modifier.weight(weight = 0.4F),
                text = empleado.nombre
            )
            Text(
                modifier = Modifier.weight(0.4F),
                text = empleado.apellido
            )

        }
    }

}


