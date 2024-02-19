package com.example.composefullequip.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composefullequip.ui.common.BottomBar
import com.example.composetokens.ui.screens.lista.ListaScreen

import com.example.composetokens.ui.screens.login.LoginScreen


@Composable
fun Navigation()
{
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
    ) {
        composable(
            "login"
        ) {

            LoginScreen (onLoginDone = {
                navController.navigate("listado")

            }
            )
        }
        composable(
            "listado"
        ) {
            ListaScreen(
          onViewDetalle = {uuid ->
                   navController.navigate("detalle/${uuid}")
             },
            bottomNavigationBar =  {
               BottomBar(
                      navController = navController,
                      screens = screensBottomBar)
              }
            )
        }

    }



}
