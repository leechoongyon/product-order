package io.simple.productOrder.domain.order

import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class OrderServiceImpl(
    private val orderStore: OrderStore,
    private val orderReader: OrderReader,

    ) : OrderService {
    @Transactional
    override fun createOrder(createOrder: OrderCommand.CreateOrder): Mono<String> {
        return Mono.just(createOrder)
            .map { it.toEntity() }
            .flatMap { orderStore.store(it) }
    }

    override fun getAllOrders(): Flux<OrderInfo.Base> {
        return orderReader.getAllOrders().flatMap { order ->
            Flux.just(Mappers.getMapper(OrderInfoMapper::class.java).convertOrderInfoBase(order))
        }
    }
}