package io.simple.productOrder.infrastructure.example

import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class RedisExampleRepository(private val reactiveRedisTemplate: ReactiveRedisTemplate<String, String>) {
    fun save(key: String, value: String): Mono<Long> {
        return reactiveRedisTemplate.opsForSet().add(key, value)
    }
}