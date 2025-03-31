package dev.chirchir.data.products.datasource.remote

import dev.chirchir.data.common.model.ErrorResponseData
import dev.chirchir.data.common.network.IBIHttpClient
import dev.chirchir.data.products.datasource.ProductsDataSource
import dev.chirchir.data.products.datasource.remote.model.ProductsResponseData
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.isSuccess

class RemoteProductsDataSourceImpl(
    private val httpClient: IBIHttpClient
): ProductsDataSource {
    override suspend fun getProducts(): ProductsResponseData {
        val response = httpClient.getClient().get("products")
        if(response.status.isSuccess()) {
            return response.body()
        }
        val error = try {
            response.body<ErrorResponseData>()
        } catch (ex: Exception) {
            ErrorResponseData("An error occurred, please try again.")
        }
        throw Exception(error.message)
    }
}