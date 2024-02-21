package com.example.composetokens.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.PersonalInjury
import androidx.compose.ui.graphics.vector.ImageVector


val screensBottomBar = listOf(
    Screens("venta", Icons.Filled.Handshake),

    Screens("cliente",Icons.Filled.PersonalInjury),
)

data class Screens(val route: String,val icon:ImageVector) {

}
