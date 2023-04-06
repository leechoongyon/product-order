package io.simple.productOrder.controller.order.v1

import java.time.LocalDateTime

class OrderDto {
    data class Request(
        val userId: String,
        val productId: String,
        val quantity: Int
    )

    data class Response(
        val message: String
    )

    data class Base(
        var id: Long,
        val orderToken: String,
        val productId: String,
        val userId: String,
        val quantity: Int,
        val orderedAt: LocalDateTime
    )
}
