package com.example.composetokens.domain.usecases

import com.example.composetokens.data.EmpleadoRepository
import javax.inject.Inject

class GetEmpleadosByIdUseCase @Inject constructor(private val empleadoRepository: EmpleadoRepository){
    operator fun invoke(id: Long) = empleadoRepository.getEmpleadosById(id)
}