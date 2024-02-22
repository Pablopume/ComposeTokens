package com.example.composetokens.domain.model

import java.time.LocalDate


data class Venta(
    val id: Long,
    val fecha: LocalDate,
    val total: Double,
    val clienteId: Long,
    val empleadoId: Long

    )