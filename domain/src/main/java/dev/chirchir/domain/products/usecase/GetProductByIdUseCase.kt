package dev.chirchir.domain.products.usecase

import dev.chirchir.domain.common.Response
import dev.chirchir.domain.common.usecase.UseCase
import dev.chirchir.domain.products.model.Product
import dev.chirchir.domain.products.repository.ProductsRepository

class GetProductByIdUseCase(
    private val productsRepository: ProductsRepository
): UseCase<Int, Product> {
    override suspend fun execute(input: Int): Response<Product> {
        return productsRepository.getProductById(input)
    }
}