package io.simple.productOrder.controller.order.v1

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

class OrderDto {
    data class Request(
        @field:NotBlank val userId: String,
        @field:NotBlank val productId: String,
        @field:Min(1) val quantity: Int
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
