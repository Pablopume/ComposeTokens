package com.example.composetokens.ui.navigation

import androidx.compose.runtime.Composable


import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composetokens.domain.model.Cliente
import com.example.composetokens.domain.model.Producto
import com.example.composetokens.ui.common.BottomBar
import com.example.composetokens.ui.screens.cliente.ClienteScreen
import com.example.composetokens.ui.screens.empleado.EmpleadoScreen
import com.example.composetokens.ui.screens.lista.ListaScreen

import com.example.composetokens.ui.screens.login.LoginScreen
import com.example.composetokens.ui.screens.producto.ProductoScreen
import com.example.composetokens.ui.screens.updateventa.UpdateVentaScreen
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

                tiendaId = it.arguments?.getLong("tiendaId") ?: 0,
                bottomNavigationBar = {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar4
                    )
                }
            )
        }
        composable("cliente") {
            ClienteScreen(

                bottomNavigationBar = {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar3
                    )
                }
            )
        }
        composable("venta") {
            VentaScreen(
                onViewDetalle = { long->
                    navController.navigate("update/${long}")
                },
                bottomNavigationBar = {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar2
                    )
                }
            )
        }
        composable("update/{ventaId}",
            arguments = listOf(navArgument(name = "ventaId") {
                type = NavType.LongType
                defaultValue = 0
            }
            )
        )
        {
            UpdateVentaScreen(onUpdated = {
                navController.navigate("venta")
            },ventaId = it.arguments?.getLong("ventaId") ?: 0

            )
        }
        composable("producto"){
            ProductoScreen(
                bottomNavigationBar = {
                    BottomBar(
                        navController = navController,
                        screens = screensBottomBar5
                    )
                }
            )
        }

    }
}
