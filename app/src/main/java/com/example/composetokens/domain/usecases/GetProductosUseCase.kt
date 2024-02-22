package com.example.composetokens.domain.usecases

import com.example.composetokens.data.ProductoRepository
import javax.inject.Inject

class GetProductosUseCase @Inject constructor(private val productoRepository: ProductoRepository){
   operator fun invoke() = productoRepository.getProductos()

}