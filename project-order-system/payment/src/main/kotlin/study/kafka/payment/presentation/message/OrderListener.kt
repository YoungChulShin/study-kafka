package study.kafka.payment.presentation.message

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import study.kafka.payment.application.PaymentService

@Component
class OrderListener(
    private val paymentService: PaymentService,
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
        val orderInfo = record.value()
        println("이벤트 수신 - $orderInfo")
        paymentService.createPayment(orderInfo.menu, orderInfo.price)
    }
}