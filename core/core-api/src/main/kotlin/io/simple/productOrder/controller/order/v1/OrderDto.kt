package io.simple.productOrder.controller.order.v1

class OrderDto {
    data class Request(
        val userId: String,
        val productId: String,
        val quantity: Int
    )

    data class Response(
        val message: String
    )
}
