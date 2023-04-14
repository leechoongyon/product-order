package io.simple.productOrder.infrastructure.product

import io.simple.productOrder.domain.product.Product
import org.springframework.data.r2dbc.repository.Modifying
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface ProductRepository : R2dbcRepository<Product, String> {
    @Modifying
    @Query("UPDATE product SET stock = stock - :quantity WHERE id = :productId")
    fun reduceStock(productId: String, quantity: Int): Mono<Int>
}