package com.example.composetokens.domain.usecases

import com.example.composetokens.data.TiendaRepository
import javax.inject.Inject

class GetTiendasUseCase @Inject constructor(private val tiendaRepository: TiendaRepository){
    operator fun invoke() = tiendaRepository.getTiendas()


}