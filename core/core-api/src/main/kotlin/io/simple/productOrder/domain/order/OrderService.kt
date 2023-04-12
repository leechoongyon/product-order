package io.simple.productOrder.domain.order

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface OrderService {
    fun createOrder(createOrder: OrderCommand.CreateOrder): Mono<String>
    fun getAllOrders(): Flux<OrderInfo.Base>
}