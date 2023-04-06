package io.simple.productOrder.domain.product

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Product(
    @Id
    val id: String,
    val name: String,
    var stock: Int
) {
    // 재고 수량을 감소시키는 메소드
    fun reduceStock(quantity: Int) {
        if (quantity > stock) {
            throw IllegalArgumentException("Insufficient stock")
        }
        stock -= quantity
    }
}