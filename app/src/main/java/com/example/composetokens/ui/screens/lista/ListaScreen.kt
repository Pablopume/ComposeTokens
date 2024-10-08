package com.example.composetokens.ui.screens.lista

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composetokens.R
import com.example.composetokens.domain.model.Tienda


@Composable
fun ListaScreen(
    viewmodel: ListaViewModel = hiltViewModel(),
    onViewDetalle: (Long?) -> Unit,
    bottomNavigationBar: @Composable () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewmodel.handleEvent(ListaEvent.GetTiendas)
    }

    val state = viewmodel.state.collectAsStateWithLifecycle()
    if (state.value.loading) {
        SplashScreenTienda()
    } else {
        ScreenContent(
            state.value,
            onViewDetalle,
            bottomNavigationBar = bottomNavigationBar
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScreenContent(
    state: ListaState,
    onViewDetalle: (Long?) -> Unit,
    bottomNavigationBar: @Composable () -> Unit
) {

    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomNavigationBar,
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

            items(items = state.lista, key = { persona -> persona.id }) { tienda ->
                TiendaItem(
                    tienda = tienda,
                    onViewDetalle = onViewDetalle,
                    modifier = Modifier.animateItemPlacement(
                        animationSpec = tween(1000)
                    )
                )
            }
        }

    }
}

@Composable
fun TiendaItem(
    tienda: Tienda,

    onViewDetalle: (Long?) -> Unit,
    modifier: Modifier = Modifier
) {

    Card(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onViewDetalle(tienda.id) }) {
        Row(modifier = Modifier.padding(8.dp)) {
            tienda.nombre?.let {
                Text(
                    modifier = Modifier.weight(weight = 0.4F),
                    text = it
                )
            }
            tienda.ubicacion?.let {
                Text(
                    modifier = Modifier.weight(0.4F),
                    text = it
                )
            }

        }
    }

}

@Composable
fun SplashScreenTienda() {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Image(painter = painterResource(id = R.drawable.tienda), contentDescription = null)
        }
    }
}





