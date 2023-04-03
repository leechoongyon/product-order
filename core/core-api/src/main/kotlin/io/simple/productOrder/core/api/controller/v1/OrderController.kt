package io.simple.productOrder.core.api.controller.v1

import io.simple.productOrder.core.api.support.response.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class OrderController() {
    @PostMapping("/api/v1/orders")
    fun createOrder(@RequestBody request: OrderDto.Request): Mono<ApiResponse<OrderDto.Response>> {
        return Mono.just(
            ApiResponse.success(OrderDto.Response("Hello World"))
        )
    }
}