package io.simple.productOrder.domain.product

sealed class ProductCommand {
    data class ReduceProduct(
        private val productId: String,
        private val quantity: Int
    ) : ProductCommand() {
        fun getProductId(): String {
            return productId
        }

        fun getQuantity(): Int {
            return quantity
        }

        companion object {
            fun of(productId: String, quantity: Int): ReduceProduct {
                return ReduceProduct(productId, quantity)
            }
        }
    }
}