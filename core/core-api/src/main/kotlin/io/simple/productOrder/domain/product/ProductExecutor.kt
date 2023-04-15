package io.simple.productOrder.domain.product

import reactor.core.publisher.Mono

interface ProductExecutor {
    fun reduceStock(reduceProduct: ProductCommand.ReduceProduct): Mono<Product>
}