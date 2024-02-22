package com.example.composetokens.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.PersonalInjury
import androidx.compose.material.icons.filled.Work
import androidx.compose.ui.graphics.vector.ImageVector


val screensBottomBar = listOf(
    Screens("venta", Icons.Filled.Handshake),

    Screens("cliente",Icons.Filled.PersonalInjury),
)
val screensBottomBar2= listOf(
    Screens("listado", Icons.Filled.Handshake),

    Screens("cliente",Icons.Filled.PersonalInjury),
)
val screensBottomBar3= listOf(
    Screens("venta", Icons.Filled.Handshake),

    Screens("listado",Icons.Filled.PersonalInjury),
)
val screensBottomBar4= listOf(
    Screens("producto", Icons.Filled.Paid),


)

val screensBottomBar5= listOf(
    Screens("listado", Icons.Filled.Handshake),


    )

data class Screens(val route: String,val icon:ImageVector) {

}
