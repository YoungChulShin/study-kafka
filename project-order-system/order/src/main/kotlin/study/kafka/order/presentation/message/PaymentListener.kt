package study.kafka.order.presentation.message

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import study.kafka.order.application.OrderService

@Component
class PaymentListener(
    private val orderService: OrderService,
    private val objectMapper: ObjectMapper,
) {

    @KafkaListener(
        topics = ["\${receiver.payment.finished.topic}"],
    )
    fun paymentFinished(record: ConsumerRecord<String, ByteArray>) {
        val orderPaymentInfo = objectMapper.readValue(record.value(), OrderPaymentInfo::class.java)
        println("결제 결과 이벤트 수신. $orderPaymentInfo")

        orderService.updatePaymentResult(orderPaymentInfo.orderId, orderPaymentInfo.success)
    }
}