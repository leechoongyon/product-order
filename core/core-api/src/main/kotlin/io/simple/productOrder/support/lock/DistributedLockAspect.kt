package io.simple.productOrder.support.lock

import io.simple.productOrder.domain.order.OrderCommand
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.redisson.api.RedissonReactiveClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.concurrent.ThreadLocalRandom

@Aspect
@Component
class DistributedLockAspect(
    private val redissonReactiveClient: RedissonReactiveClient
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Around("@annotation(DistributedLock)")
    fun lock(joinPoint: ProceedingJoinPoint): Mono<Any?> {
        logger.info("joinPoint.args : {}", joinPoint.args)
        var lockKey = getLockKey(joinPoint.args)
        val lock = redissonReactiveClient.getLock(lockKey)
        var threadId = ThreadLocalRandom.current().nextLong();
        return lock.tryLock(threadId)
            .flatMap { lockAcquired ->
                logger.info("lockAcquired : {}", lockAcquired)
                if (lockAcquired) {
                    logger.info("Lock acquired: $lockKey")
                    try {
                        joinPoint.proceed() as Mono<Any?>
                    } finally {
                        lock.unlock(threadId).subscribe {
                            logger.info("Lock released: $lockKey")
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
