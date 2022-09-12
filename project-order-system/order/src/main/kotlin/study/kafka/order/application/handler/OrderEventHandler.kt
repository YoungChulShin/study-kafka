package study.kafka.order.application.handler

import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener
import study.kafka.order.domain.OrderRepository
import study.kafka.order.domain.events.OrderCreated
import study.kafka.order.domain.model.OrderInfo
import study.kafka.order.domain.transmitter.OrderTransmitter

@Component
class OrderEventHandler(
    val orderTransmitter: OrderTransmitter,
    val orderRepository: OrderRepository,
) {

    @TransactionalEventListener
    fun handleOrderCreatedEvent(event: OrderCreated) {
        val order = orderRepository.findById(event.id!!).get()

        orderTransmitter.transmitOrderCreated(OrderInfo.from(order))
    }
}