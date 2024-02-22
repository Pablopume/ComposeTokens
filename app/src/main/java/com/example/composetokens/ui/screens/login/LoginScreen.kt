package com.example.composetokens.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composetokens.CredentialsRegister


@Composable
fun LoginScreen(viewmodel: LoginViewModel = hiltViewModel(), onLoginDone: () -> Unit) {
    val state = viewmodel.state.collectAsStateWithLifecycle()

    ScreenContent(
        onLoginDone,
        state.value
    ) { when (it) {
        is LoginEvent.Login -> viewmodel.handleEvent(it)
        is LoginEvent.Register -> viewmodel.handleEvent(it)
    }
}
}
@Composable
private fun ScreenContent(
    onLoginDone: () -> Unit,
    value: PantallaLoginState,
    function: (LoginEvent) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { innerPadding ->
        LaunchedEffect(value.error) {
            value.error?.let {
                snackbarHostState.showSnackbar(
                    message = value.error.toString(),
                    duration = SnackbarDuration.Short,
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(), // Centrar los elementos en la pantalla
            horizontalAlignment = Alignment.CenterHorizontally, // Alinear horizontalmente al centro
            verticalArrangement = Arrangement.Center // Alinear verticalmente al centro
        ) {
            Text(text = "Iniciar sesión", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it; value.username = it },
                label = { Text("Usuario") },
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it; value.password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                function(LoginEvent.Login)
            }) {
                Text("Iniciar sesión")
            }
            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre los botones
            Button(onClick = {
                function(LoginEvent.Register(CredentialsRegister(username, password)))
            }) {
                Text("Registrarse")
            }
        }
        }

    LaunchedEffect(value.logged) {
        if (value.logged) {
            onLoginDone()
        }
    }
}

