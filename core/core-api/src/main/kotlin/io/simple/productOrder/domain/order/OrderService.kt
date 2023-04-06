package io.simple.productOrder.domain.order

import io.simple.productOrder.controller.order.v1.OrderDto
import reactor.core.publisher.Mono

interface OrderService {
    fun createOrder(createOrder: OrderCommand.CreateOrder): Mono<String>
    fun getAllOrders(): Mono<List<OrderDto.Base>>
}