package io.simple.productOrder.controller.order.v1

import io.simple.productOrder.domain.order.OrderService
import io.simple.productOrder.support.response.ApiResponse
import org.mapstruct.factory.Mappers
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class OrderController(private val orderService: OrderService) {
    @PostMapping("/api/v1/orders")
    fun createOrder(@RequestBody request: OrderDto.Request): Mono<ApiResponse<OrderDto.Response>> {
        val orderDtoMapper = Mappers.getMapper(OrderDtoMapper::class.java)
        orderService.createOrder(orderDtoMapper.convertToOrderCommandCreateOrder(request));
        return Mono.just(ApiResponse.success(OrderDto.Response("Order Success")))
    }
}