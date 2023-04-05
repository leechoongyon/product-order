package io.simple.productOrder.domain.order

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderService {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    fun createOrder(createOrder: OrderCommand.CreateOrder) {
        log.info("createOrder : {}", createOrder)
    }
}