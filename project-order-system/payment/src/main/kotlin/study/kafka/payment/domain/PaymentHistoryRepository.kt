package study.kafka.payment.domain

import org.springframework.data.jpa.repository.JpaRepository

interface PaymentHistoryRepository : JpaRepository<PaymentHistory, Long>