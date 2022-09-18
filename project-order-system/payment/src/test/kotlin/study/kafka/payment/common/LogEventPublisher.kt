package study.kafka.payment.common

import org.springframework.context.ApplicationEventPublisher

class LogEventPublisher: ApplicationEventPublisher {

    val events = mutableListOf<Any>()

    override fun publishEvent(event: Any) {
        events.add(event)
    }

    fun eventCount() = this.events.count()
}