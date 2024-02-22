package com.example.composetokens.domain.usecases

import com.example.composetokens.data.EmpleadoRepository
import com.example.composetokens.domain.model.Empleado
import com.serverschema.type.EmpleadoInput
import javax.inject.Inject

class AddEmpleadoUseCase @Inject constructor(private val empleadoRepository: EmpleadoRepository){
    operator fun invoke(empleadoinput: Empleado) = empleadoRepository.addEmpleado(empleadoinput)
}