package io.simple.productOrder.infrastructure.order

import io.simple.productOrder.domain.order.Order
import io.simple.productOrder.domain.order.OrderReader
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class OrderReaderImpl(private val orderRepository: OrderRepository) : OrderReader {
    override fun getOrder(id: Long): Mono<Order> {
        return orderRepository.findById(id)
    }
}