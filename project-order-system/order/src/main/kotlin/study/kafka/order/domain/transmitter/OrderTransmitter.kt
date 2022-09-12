package study.kafka.order.domain.transmitter

import study.kafka.order.domain.model.OrderInfo

interface OrderTransmitter {

    fun transmitOrderCreated(orderInfo: OrderInfo)
}