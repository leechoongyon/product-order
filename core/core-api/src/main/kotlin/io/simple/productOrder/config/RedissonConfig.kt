package io.simple.productOrder.config

import org.redisson.Redisson
import org.redisson.api.RedissonReactiveClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedissonConfig {
    @Value("\${spring.redis.host}")
    private lateinit var redisHost: String

    @Value("\${spring.redis.port}")
    private lateinit var redisPort: String

    @Value("\${spring.redis.password}")
    private lateinit var redisPassword: String

    @Bean
    fun redissonReactiveClient(): RedissonReactiveClient {
        val config = Config()
        config.useSingleServer()
            .setAddress("redis://${redisHost}:${redisPort}")
//            .setPassword(redisPassword)   // 이거 만약 패스워드가 존재하면 이거 풀어야 함.
        return Redisson.create(config).reactive()
    }
}