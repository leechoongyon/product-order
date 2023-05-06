package io.simple.productOrder.support.lock

import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import java.time.Duration

@Component
class DistributedLockService(
    private val reactiveRedisTemplate: ReactiveRedisTemplate<String, String>,
) {
    companion object {
        private const val DEFAULT_TIMEOUT = 5000L
    }

    fun acquireLock(key: String): Mono<Boolean> {
        // Redis의 SETNX 명령어를 사용하여 락 획득 (Atomic)
        // key : 저장할 키, "" : 저장 value, timeout : TTL
        return reactiveRedisTemplate.opsForValue()
            .setIfAbsent(
                key, "", Duration.ofMillis(DEFAULT_TIMEOUT)
            )
    }

    fun releaseLock(key: String): Mono<Long> {
        // Redis의 DEL 명령어를 사용하여 락 해제 (Atomic)
        return reactiveRedisTemplate.delete(key)
    }
}