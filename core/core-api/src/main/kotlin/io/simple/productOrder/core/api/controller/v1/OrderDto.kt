package io.simple.productOrder.core.api.controller.v1

class OrderDto {
    data class Request(
        val userId: String,
        val productId: String
    )

    data class Response(
        val message: String
    )
}
