package study.kafka.payment.application.model

data class CreatePaymentCommand(
    val orderId: Long,
    val menu: String,
    val price: Int
)