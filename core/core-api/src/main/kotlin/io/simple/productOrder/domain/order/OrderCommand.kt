package io.simple.productOrder.domain.order

sealed class OrderCommand {
    data class CreateOrder(
        val userId: String,
        val productId: String,
        val quantity: Int
    ) : OrderCommand()

    fun toEntity(): Order {
        return when (this) {
            is CreateOrder -> {
                Order(
                    productId = productId,
                    userId = userId,
                    quantity = quantity
                )
            }
        }
    }
}