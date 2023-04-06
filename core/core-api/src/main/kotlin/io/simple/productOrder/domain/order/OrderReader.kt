package io.simple.productOrder.domain.order

import reactor.core.publisher.Mono

interface OrderReader {
    fun getOrder(id: Long): Mono<Order>
    fun getAllOrders(): Mono<List<Order>>
}