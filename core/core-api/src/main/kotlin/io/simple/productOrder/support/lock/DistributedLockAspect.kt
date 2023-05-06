package io.simple.productOrder.support.lock

import io.simple.productOrder.domain.order.OrderCommand
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Aspect
@Component
class DistributedLockAspect(private val distributedLockService: DistributedLockService) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Around("@annotation(DistributedLock)")
    fun lock(joinPoint: ProceedingJoinPoint): Mono<Any?> {
        var lockKey = getLockKey(joinPoint.args)
        return distributedLockService.acquireLock(lockKey)
            .flatMap { lockAcquired ->
                if (lockAcquired) {
                    logger.debug("Lock acquired: $lockKey")
                    try {
                        joinPoint.proceed() as Mono<Any?>
                    } finally {
                        distributedLockService.releaseLock(lockKey).subscribe {
                            logger.debug("Lock released: $lockKey")
                        }
                    }
                } else {
                    logger.debug("Failed to acquire lock: $lockKey")
                    Mono.empty<Any>()
                }
            }
            .switchIfEmpty(Mono.error(Exception("락을 획득하지 못했습니다.")))
    }

    private fun getLockKey(args: Array<Any>): String {
        require(args.isNotEmpty()) { "Arguments must not be empty" }
        val arg = args[0]
        return when (arg) {
            is String -> arg
            is OrderCommand.CreateOrder -> {
                requireNotNull(arg.getProductId()) { "ProductId must not be null" }
                "product:${arg.getProductId()}"
            }

            else -> throw IllegalArgumentException("Unsupported argument type: ${arg.javaClass.name}")
        }
    }
}
