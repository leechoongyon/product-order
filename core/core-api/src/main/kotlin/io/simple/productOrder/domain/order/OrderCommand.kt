package io.simple.productOrder.domain.order

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

sealed class OrderCommand {
    data class CreateOrder(
        @field:NotBlank private val userId: String,
        @field:NotBlank private val productId: String,
        @field:Min(1) private val quantity: Int
    ) : OrderCommand() {
        fun toEntity(): Order {
            return Order(
                productId = productId,
                userId = userId,
                quantity = quantity
            )
        }

        fun getUserId(): String {
            return userId
        }

        fun getProductId(): String {
            return productId
        }

        fun getQuantity(): Int {
            return quantity
        }
    }
}