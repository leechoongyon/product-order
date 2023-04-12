package io.simple.productOrder.domain.order

import reactor.core.publisher.Flux

interface OrderReader {
    fun getAllOrders(): Flux<Order>
}