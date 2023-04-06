package io.simple.productOrder.domain.order

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("product_order")
data class Order(
    @Id
    var id: Long? = null,
    val orderToken: String = UUID.randomUUID().toString(),
    val productId: String,
    val userId: String,
    val quantity: Int,
    val orderedAt: LocalDateTime = LocalDateTime.now()
)