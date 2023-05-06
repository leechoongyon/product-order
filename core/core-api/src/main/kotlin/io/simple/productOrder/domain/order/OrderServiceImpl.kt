package io.simple.productOrder.domain.order

import io.simple.productOrder.domain.product.ProductCommand
import io.simple.productOrder.domain.product.ProductExecutor
import io.simple.productOrder.support.lock.DistributedLock
import io.simple.productOrder.support.lock.DistributedLockService
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class OrderServiceImpl(
    private val orderStore: OrderStore,
    private val orderReader: OrderReader,
    private val productExecutor: ProductExecutor,
    private val distributedLockService: DistributedLockService
) : OrderService {

    companion object {
        const val LOCK_KEY_PREFIX = "product_"
    }

    @Transactional
    @DistributedLock(key = LOCK_KEY_PREFIX + "{createOrderRequest.productId}")
    override fun createOrder(createOrderRequest: OrderCommand.CreateOrder): Mono<String> {
        return productExecutor.reduceStock(
            ProductCommand.ReduceProduct.of(
                createOrderRequest.getProductId(),
                createOrderRequest.getQuantity()
            )
        )
            .flatMap {
                Mono.just(createOrderRequest)
                    .map { it.toEntity() }
                    .flatMap { orderStore.store(it) }
            }
            .onErrorResume { e ->
                // 예외 처리 로직
                Mono.error(Exception("createOrder 처리 중 예외 발생", e))
            }
    }

    override fun getAllOrders(): Flux<OrderInfo.Base> {
        return orderReader.getAllOrders().flatMap { order ->
            Flux.just(Mappers.getMapper(OrderInfoMapper::class.java).convertOrderInfoBase(order))
        }
    }
}