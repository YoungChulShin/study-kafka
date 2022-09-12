package study.kafka.order.domain.transmitter

import study.kafka.order.domain.Order

interface OrderTransmitter {

    fun transmitOrderCreated(order: Order)
}