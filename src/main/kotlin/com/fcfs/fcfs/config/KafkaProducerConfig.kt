package com.fcfs.fcfs.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.LongSerializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory


@Configuration
class KafkaProducerConfig {

    @Bean
    fun producerFactory(): ProducerFactory<String, Long> {
        // ProducerFactory<String, Long> : key는 Sring, value는 Long으로 지정한다.
        // 설정 값을 담아줄 map을 변수로 선언한다.
        val config: MutableMap<String, Any> = HashMap()

        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:10000,localhost:10001,localhost:10002"

        // 메시지 키로 “user123” 과 같은 문자열을 사용한다.
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java

        // 메시지 값으로 1L, 2L, 3L과 같은 Long 타입을 사용한다.
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = LongSerializer::class.java

        return DefaultKafkaProducerFactory<String, Long>(config)
    }

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Long> {
        return KafkaTemplate<String, Long>(producerFactory())
    }
}