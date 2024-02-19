package com.example.composetokens.data

import com.example.composetokens.domain.model.Tienda
import com.serverschema.GetTiendasQuery

fun GetTiendasQuery.GetTienda.toTiendas(): Tienda {
    return Tienda(
        id = id?.toLong(),
         nombre = nombre,
        ubicacion = ubicacion,


    )
}