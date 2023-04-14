package io.simple.productOrder.domain.product

interface ProductExecutor {
    fun reduceStock(reduceProduct: ProductCommand.ReduceProduct)
}