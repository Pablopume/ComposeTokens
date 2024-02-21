package com.example.composetokens.data

import com.example.composetokens.domain.model.Cliente
import com.example.composetokens.domain.model.Empleado
import com.example.composetokens.domain.model.Tienda
import com.example.composetokens.domain.model.Venta
import com.serverschema.GetClienteQuery
import com.serverschema.GetEmpleadosByIdTiendaQuery
import com.serverschema.GetTiendasQuery
import com.serverschema.GetVentasQuery
import com.serverschema.type.UpdateVentaInput


fun GetTiendasQuery.GetTienda.toTiendas(): Tienda {
    return Tienda(
        id = id?.toLong() ?: 0L,
        nombre = nombre,
        ubicacion = ubicacion,


        )

}

fun GetEmpleadosByIdTiendaQuery.GetEmpleadosByIdTienda.toEmpleado(): Empleado {
    return Empleado(
        id = id.toLong(),
        nombre = nombre,
        apellido = apellido,
        cargo = cargo,
    )
}

fun GetClienteQuery.GetCliente.toCliente(): Cliente {
    return Cliente(
        id = id.toLong(),
        nombre = nombre,
        email = email,
    )


}

fun GetVentasQuery.GetVenta.toVenta(): Venta {
    return Venta(
        id = id.toLong(),
        fecha = fecha,
        total = total,
    )
}

fun Venta.toUpdateVentaInput(venta: Venta): UpdateVentaInput {
    return UpdateVentaInput(
        id = venta.id.toString(),
        fecha = venta.fecha.toString(),
        total = venta.total,
    )
}

