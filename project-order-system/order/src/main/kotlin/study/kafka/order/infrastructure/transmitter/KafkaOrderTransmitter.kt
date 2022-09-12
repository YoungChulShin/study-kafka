package study.kafka.order.infrastructure.transmitter

import org.springframework.stereotype.Component
import study.kafka.order.domain.Order
import study.kafka.order.domain.transmitter.OrderTransmitter

@Component
class KafkaOrderTransmitter: OrderTransmitter {

    override fun transmitOrderCreated(order: Order) {
        println("카프카 데이터 전달")
    }
}