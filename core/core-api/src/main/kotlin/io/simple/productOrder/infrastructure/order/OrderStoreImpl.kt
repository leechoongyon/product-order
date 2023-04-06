package io.simple.productOrder.infrastructure.order

import io.simple.productOrder.domain.order.Order
import io.simple.productOrder.domain.order.OrderStore
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class OrderStoreImpl(private val orderRepository: OrderRepository) : OrderStore {
    override fun store(order: Order): Mono<String> {
        return orderRepository.save(order).map { it.orderToken }
    }
}