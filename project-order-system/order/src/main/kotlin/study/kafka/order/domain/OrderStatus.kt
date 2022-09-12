package study.kafka.order.domain

enum class OrderStatus {

    CREATED,
    PAYMENT_COMPLETED,
    CANCELLED,
}