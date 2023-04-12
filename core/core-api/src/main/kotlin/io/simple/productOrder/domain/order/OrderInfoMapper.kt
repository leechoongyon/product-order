package io.simple.productOrder.domain.order

import org.mapstruct.Mapper

@Mapper
interface OrderInfoMapper {
    fun convertOrderInfoBase(request: Order): OrderInfo.Base
}