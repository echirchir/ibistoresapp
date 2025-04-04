package dev.chirchir.domain.products.usecase

import dev.chirchir.domain.common.Response
import dev.chirchir.domain.common.usecase.UseCase
import dev.chirchir.domain.products.model.Product
import dev.chirchir.domain.products.repository.ProductsRepository

class UpdateProductUseCase(
    private val productsRepository: ProductsRepository
): UseCase<Product, Unit> {
    override suspend fun execute(input: Product): Response<Unit> {
        return productsRepository.updateProduct(input)
    }
}