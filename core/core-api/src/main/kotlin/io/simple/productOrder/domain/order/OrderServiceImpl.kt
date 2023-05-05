package io.simple.productOrder.domain.order

import io.simple.productOrder.domain.product.ProductCommand
import io.simple.productOrder.domain.product.ProductExecutor
import io.simple.productOrder.support.lock.DistributedLock
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
    private val distributedLock: DistributedLock
) : OrderService {

    @Transactional
    override fun createOrder(createOrder: OrderCommand.CreateOrder): Mono<String> {
        val lockKey = "product_${createOrder.getProductId()}"
        return distributedLock.acquireLock(lockKey)
            .flatMap { lockAcquired ->
                if (lockAcquired) {
                    // 락 획득 시, 주문을 생성
                    productExecutor.reduceStock(
                        ProductCommand.ReduceProduct.of(
                            createOrder.getProductId(),
                            createOrder.getQuantity()
                        )
                    )
                        .flatMap {
                            Mono.just(createOrder)
                                .map { it.toEntity() }
                                .flatMap { orderStore.store(it) }
                        }
                        .doFinally {
                            // 주문 생성 후, 락 해제
                            distributedLock.releaseLock(lockKey).subscribe()
                        }
                } else {
                    // 락을 획득하지 못한 경우 -> Mono.empty()를 반환
                    Mono.empty()
                }
            }
            .onErrorResume { e ->
                // 에러 발생 시, 락 해제
                distributedLock.releaseLock(lockKey).then(Mono.error(e))
            }
            .switchIfEmpty(Mono.error(Exception("락을 획득하지 못했습니다.")))
    }

    override fun getAllOrders(): Flux<OrderInfo.Base> {
        return orderReader.getAllOrders().flatMap { order ->
            Flux.just(Mappers.getMapper(OrderInfoMapper::class.java).convertOrderInfoBase(order))
        }
    }
}