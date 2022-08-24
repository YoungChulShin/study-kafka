package study.kafka.kafkaproducer.controller.model

data class UserEventDto(
    val timestamp: String,
    val userAgent: String,
    val colorName: String,
    val userName: String,
)