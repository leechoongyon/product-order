package io.simple.productOrder.controller.order.v1

import io.simple.productOrder.domain.order.OrderCommand
import org.mapstruct.Mapper

@Mapper
interface OrderDtoMapper {
    fun convertToOrderCommandCreateOrder(request: OrderDto.Request): OrderCommand.CreateOrder
}

