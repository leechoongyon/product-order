package io.simple.productOrder.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.RedisSerializationContext

@Configuration
@EnableRedisRepositories(basePackages = ["io.simple.productOrder.infrastructure"])
class RedisConfig {
    @Value("\${spring.redis.host}")
    private lateinit var redisHost: String

    @Value("\${spring.redis.port}")
    private lateinit var redisPort: String

    @Value("\${spring.redis.password}")
    private lateinit var redisPassword: String

    @Bean
    fun lettuceConnectionFactory(): LettuceConnectionFactory {
        val redisStandaloneConfiguration = RedisStandaloneConfiguration(
            redisHost,
            redisPort.toInt()
        )
        redisStandaloneConfiguration.setPassword(redisPassword)

        return LettuceConnectionFactory(
            redisStandaloneConfiguration,
            LettuceClientConfiguration.builder().build()
        )
    }

    @Bean
    fun reactiveRedisTemplate(): ReactiveRedisTemplate<String, String> {
        return ReactiveRedisTemplate(lettuceConnectionFactory(), RedisSerializationContext.string())
    }
}
