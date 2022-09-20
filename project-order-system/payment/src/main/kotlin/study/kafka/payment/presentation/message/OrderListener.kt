package study.kafka.payment.presentation.message

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import study.kafka.payment.application.PaymentService
import study.kafka.payment.application.model.CreatePaymentCommand

@Component
class OrderListener(
    private val paymentService: PaymentService,
    private val objectMapper: ObjectMapper,
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
    fun orderCreatedListener(record: ConsumerRecord<String, ByteArray>) {
        val orderInfo = objectMapper.readValue(record.value(), OrderInfo::class.java)
        println("이벤트 수신 - $orderInfo")
        paymentService.createPayment(
            CreatePaymentCommand(
                orderInfo.id,
                orderInfo.menu,
                orderInfo.price
            )
        )
    }
}