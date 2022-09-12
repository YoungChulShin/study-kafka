package study.kafka.order.application

import org.springframework.context.ApplicationEventPublisher

class TestEventPublisher : ApplicationEventPublisher {

    val events = mutableListOf<Any>()

    override fun publishEvent(event: Any) {
        events.add(event)
    }
}