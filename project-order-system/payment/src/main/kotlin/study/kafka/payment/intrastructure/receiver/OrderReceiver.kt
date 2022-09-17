package study.kafka.payment.intrastructure.receiver

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.context.ApplicationEventPublisher
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class OrderReceiver(
    private val eventPublisher: ApplicationEventPublisher
) {

    companion object {
        const val ORDER_CREATED_TOPIC = "order-created"
        const val ORDER_CREATED_GROUP_ID = "payment-group-1"
    }

    @KafkaListener(
        topics = [ORDER_CREATED_TOPIC],
        groupId = ORDER_CREATED_GROUP_ID,
        containerFactory = "orderCreatedContainerFactory"
    )
    fun orderCreatedListener(record: ConsumerRecord<String, OrderInfo>) {
        println("이벤트 수신 - ${record.value()}")
        eventPublisher.publishEvent(record.value())
    }
}