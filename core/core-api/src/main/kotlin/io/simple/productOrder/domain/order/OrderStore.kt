package io.simple.productOrder.domain.order

import reactor.core.publisher.Mono

interface OrderStore {
    fun store(order: Order): Mono<String>
}