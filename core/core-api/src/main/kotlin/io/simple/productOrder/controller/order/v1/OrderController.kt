package io.simple.productOrder.controller.order.v1

import io.simple.productOrder.domain.order.OrderService
import io.simple.productOrder.support.response.ApiResponse
import jakarta.validation.Valid
import org.mapstruct.factory.Mappers
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class OrderController(
    private val orderService: OrderService,
) {
    @PostMapping("/api/v1/orders")
    fun createOrder(@RequestBody @Valid request: OrderDto.Request): Mono<ApiResponse<OrderDto.Response>> {
        return orderService.createOrder(
            Mappers.getMapper(OrderDtoMapper::class.java).convertToOrderCommandCreateOrder(request)
        )
            .map { orderToken ->
                ApiResponse.success(
                    OrderDto.Response(orderToken)
                )
            }
    }

    @GetMapping("/api/v1/orders")
    fun getAllOrders(): Flux<OrderDto.Base> {
        return orderService.getAllOrders()
            .map { orderInfo ->
                Mappers.getMapper(OrderDtoMapper::class.java).convertToOrderDtoBase(orderInfo)
            }
    }
}