package io.simple.productOrder.domain.order

sealed class OrderCommand {
    data class CreateOrder(
        private val userId: String,
        private val productId: String,
        private val quantity: Int
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