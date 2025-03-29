package com.fcfs.fcfs.service

import com.fcfs.fcfs.controller.CouponController
import lombok.RequiredArgsConstructor
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class CouponCreateProducer(
    private val kafkaTemplate: KafkaTemplate<String, Long>,
) {
    private val log: Logger = LoggerFactory.getLogger(CouponController::class.java)

    companion object {
        private val TOPIC_NAME: String = "coupon-create"
    }

    fun create(userId: Long) {
        // 카프카 템플릿을 사용해서 coupon-create 토픽에 userId 를 전송한다. (메시지 발행)
        kafkaTemplate.send(TOPIC_NAME, userId)
        log.info("메시지 발행 성공, topicName: {}, userId: {}", TOPIC_NAME, userId)
    }
}