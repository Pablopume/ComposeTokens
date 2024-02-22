package com.example.composetokens.data

import com.example.composetokens.domain.model.Cliente
import com.example.composetokens.domain.model.Empleado
import com.example.composetokens.domain.model.Producto
import com.example.composetokens.domain.model.Tienda
import com.example.composetokens.domain.model.Venta
import com.serverschema.GetClienteQuery
import com.serverschema.GetEmpleadosByIdTiendaQuery
import com.serverschema.GetProductosQuery
import com.serverschema.GetTiendasQuery
import com.serverschema.GetVentaByIdQuery
import com.serverschema.GetVentasQuery
import com.serverschema.type.EmpleadoInput
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
        tiendaId = 0
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
        clienteId = clienteId.toLong(),
        empleadoId = empleadoId.toLong()
    )
}

fun Venta.toUpdateVentaInput(venta: Venta): UpdateVentaInput {
    return UpdateVentaInput(
        id = venta.id.toString(),
        fecha = venta.fecha.toString(),
        total = venta.total,
        clienteId = venta.clienteId.toString(),
        empleadoId = venta.empleadoId.toString()
    )
}

fun Empleado.toEmpleadoInput(empleado: Empleado): EmpleadoInput {
    return EmpleadoInput(
        nombre = empleado.nombre,
        apellido = empleado.apellido,
        cargo = empleado.cargo,
        tiendaId = empleado.tiendaId.toString()
    )
}
    fun GetVentaByIdQuery.GetVentaById.toVenta(): Venta {
        return Venta(
            id = id.toLong(),
            fecha = fecha,
            total = total,
            clienteId = clienteId.toLong(),
            empleadoId = empleadoId.toLong()
        )
    }
        fun GetProductosQuery.GetProducto.toProducto(): Producto {
            return Producto(
                id = id.toLong(),
                nombre = nombre,
                precio = precio,
                stock = stock
            )

    }



