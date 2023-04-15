package io.simple.productOrder.controller.example

import io.simple.productOrder.infrastructure.example.RedisExampleRepository
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class RedisExampleController(private val redisExampleRepository: RedisExampleRepository) {
    @PostMapping("/example/redis")
    fun saveExample(@RequestParam key: String, @RequestParam value: String): Mono<Long> {
        return redisExampleRepository.save(key, value)
    }
}