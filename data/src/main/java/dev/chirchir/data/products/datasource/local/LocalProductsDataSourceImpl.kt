package dev.chirchir.data.products.datasource.local

import dev.chirchir.data.common.network.IBIHttpClient

class LocalProductsDataSourceImpl(
    private val httpClient: IBIHttpClient
)