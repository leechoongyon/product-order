package io.simple.productOrder.domain.order

sealed class OrderCommand {

    data class CreateOrder(
        val userId: String,
        val productId: String,
        val quantity: Int
    ) : OrderCommand()
}