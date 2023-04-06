package io.simple.productOrder.infrastructure.order

import io.simple.productOrder.domain.order.Order
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : R2dbcRepository<Order, Long>