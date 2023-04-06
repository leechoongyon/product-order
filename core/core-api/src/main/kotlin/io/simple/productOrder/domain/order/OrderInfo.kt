package io.simple.productOrder.domain.order

import java.time.LocalDateTime

class OrderInfo {
    data class Base(
        var id: Long,
        val orderToken: String,
        val productId: String,
        val userId: String,
        val quantity: Int,
        val orderedAt: LocalDateTime
    )
}