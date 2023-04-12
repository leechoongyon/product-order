package io.simple.productOrder.infrastructure.order

import io.simple.productOrder.domain.order.Order
import io.simple.productOrder.domain.order.OrderReader
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class OrderReaderImpl(private val orderRepository: OrderRepository) : OrderReader {
    override fun getAllOrders(): Flux<Order> {
        return orderRepository.findAll();
    }
}