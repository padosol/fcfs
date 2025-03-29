package com.fcfs.fcfs.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository

@Repository
class CouponCountRepository(
    private val redisTemplate: RedisTemplate<String, String>
) {

    fun increment(): Long? {
        return redisTemplate.opsForValue().increment("coupon-count")
    }

    fun deleteByKey(key: String) {
        redisTemplate.delete(key)
    }
}