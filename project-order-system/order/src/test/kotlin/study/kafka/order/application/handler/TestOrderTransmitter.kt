package study.kafka.order.application.handler

import study.kafka.order.domain.model.OrderInfo
import study.kafka.order.domain.transmitter.OrderTransmitter

class TestOrderTransmitter : OrderTransmitter {

    val orderInfos = mutableListOf<OrderInfo>()

    override fun transmitOrderCreated(orderInfo: OrderInfo) {
        orderInfos.add(orderInfo)
    }
}