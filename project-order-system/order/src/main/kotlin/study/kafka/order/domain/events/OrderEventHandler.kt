package study.kafka.order.domain.events

import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class OrderEventHandler {

    @TransactionalEventListener
    fun handleOrderCreatedEvent(event: OrderCreated) {
        println("오더 생성 완료")
    }
}