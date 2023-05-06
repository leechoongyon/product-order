package io.simple.productOrder.support.lock

import org.redisson.api.RedissonReactiveClient
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.util.concurrent.TimeUnit

@Component
class DistributedLockService(
    private val redissonReactiveClient: RedissonReactiveClient
) {
    companion object {
        private const val DEFAULT_TIMEOUT = 5000L
        private const val DEFAULT_LEASE_TIME = 5000L
    }

    fun acquireLock(key: String, threadId: Long): Mono<Boolean> =
        redissonReactiveClient.getLock(key)
            .tryLock(
                DEFAULT_TIMEOUT,
                DEFAULT_LEASE_TIME,
                TimeUnit.MILLISECONDS,
                threadId
            )
            .map { lockAcquired ->
                if (lockAcquired) true
                else throw IllegalStateException("Failed to acquire lock for key $key")
            }

    fun releaseLock(key: String, threadId: Long): Mono<Void> =
        redissonReactiveClient.getLock(key)
            .unlock(threadId)
            .then()
}