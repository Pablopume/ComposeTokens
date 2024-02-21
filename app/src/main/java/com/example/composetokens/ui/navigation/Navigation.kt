package com.example.composetokens.ui.navigation

import androidx.compose.runtime.Composable


import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composetokens.domain.model.Cliente
import com.example.composetokens.ui.common.BottomBar
import com.example.composetokens.ui.screens.cliente.ClienteScreen
import com.example.composetokens.ui.screens.empleado.EmpleadoScreen
import com.example.composetokens.ui.screens.lista.ListaScreen

import com.example.composetokens.ui.screens.login.LoginScreen
import com.example.composetokens.ui.screens.venta.VentaScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
    ) {
        composable(
            "login"
        ) {

            LoginScreen(onLoginDone = {
                navController.navigate("listado") {
                    popUpTo("login") {
                        inclusive = true
                    }
                }

            }
            )
        }
        composable(
            "listado"
        ) {
            ListaScreen(
                onViewDetalle = { long ->
                    navController.navigate("detalle/${long}")
                },
                bottomNavigationBar = {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar
                    )
                }
            )
        }
        composable(
            "detalle/{tiendaId}",
            arguments = listOf(navArgument(name = "tiendaId") {
                type = NavType.LongType
                defaultValue = 0
            }
            )
        )
        {
            EmpleadoScreen(
                onViewDetalle = { long ->
                    navController.navigate("detalle/${long}")
                },
                tiendaId = it.arguments?.getLong("tiendaId") ?: 0,
                bottomNavigationBar = {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar
                    )
                }
            )
        }
        composable("cliente") {
            ClienteScreen(

                bottomNavigationBar = {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar
                    )
                }
            )
        }
        composable("venta") {
            VentaScreen(
                onViewDetalle = { long ->
                    navController.navigate("update/${long}")
                },
                bottomNavigationBar = {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar
                    )
                }
            )
        }


    }
}
