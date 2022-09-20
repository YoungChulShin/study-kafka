package study.kafka.payment.intrastructure.transmitter

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFutureCallback
import study.kafka.payment.domain.OrderPaymentInfo
import study.kafka.payment.domain.transmitter.PaymentTransmitter

@Component
class KafkaPaymentTransmitter(
    private val kafkaTemplate: KafkaTemplate<String, ByteArray>,
    @Value("\${transmitter.payment.finished.topic}") private val topic: String,
    private val objectMapper: ObjectMapper,
) : PaymentTransmitter {

    override fun transmitPaymentFinished(orderPaymentInfo: OrderPaymentInfo) {
        val result = kafkaTemplate.send(
            topic,
            orderPaymentInfo.orderId.toString(),
            objectMapper.writeValueAsBytes(orderPaymentInfo)
        )

        result.addCallback(object: ListenableFutureCallback<SendResult<String, ByteArray>> {
            override fun onSuccess(result: SendResult<String, ByteArray>?) {
                println("결제 결과 전송 성공")
            }

            override fun onFailure(ex: Throwable) {
                println("결제 결과 전송 실패")
            }
        })
    }
}