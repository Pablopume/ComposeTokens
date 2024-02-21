package com.example.composetokens.ui.screens.venta

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
import com.example.composetokens.domain.model.Tienda
import com.example.composetokens.domain.model.Venta


@Composable
fun VentaScreen(viewmodel: VentaViewModel = hiltViewModel(),onViewDetalle: (Long?) -> Unit, bottomNavigationBar : @Composable () -> Unit = {}) {

    val state = viewmodel.state.collectAsStateWithLifecycle()
    ScreenContent(state.value,onViewDetalle,bottomNavigationBar= bottomNavigationBar){viewmodel.handleEvent(VentaEvent.GetVentas)}
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreenContent(
    state: VentaState,
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

            items(items = state.lista, key = { persona -> persona.id }) {
                    persona ->
                TiendaItem(persona = persona,onViewDetalle = onViewDetalle,
                    modifier = Modifier.animateItemPlacement(
                        animationSpec = tween(1000)
                    ))
            }
        }

    }

}

@Composable
fun TiendaItem(persona: Venta,

               onViewDetalle: (Long?) -> Unit,
               modifier: Modifier = Modifier){

    Card(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable(onClick = { onViewDetalle(persona.id) })
       ) {
        Row( modifier = Modifier.padding(8.dp)){
            Text(
                text = persona.total.toString()+"€"
            )


        }
    }

}


