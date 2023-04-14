package io.simple.productOrder.domain.product

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("product")
class Product(
    @Id
    private val id: String,
    private val name: String,
    private var stock: Int
) {
    fun getId(): String = id
    fun getName(): String = name
    fun getStock(): Int = stock
}