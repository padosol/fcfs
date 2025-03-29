package com.fcfs.fcfs.service

import com.fcfs.fcfs.controller.CouponController
import com.fcfs.fcfs.entity.Coupon
import com.fcfs.fcfs.repository.CouponCountRepository
import com.fcfs.fcfs.repository.CouponRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CouponService(
    private val couponRepository: CouponRepository,
    private val couponCountRepository: CouponCountRepository,
    private val couponCreateProducer: CouponCreateProducer
) {
    private val log: Logger = LoggerFactory.getLogger(CouponController::class.java)

    
    // 쿠폰 발급 로직
    fun apply(userId: Long) {
        // 쿠폰 개수 조회(postgresql)
//        val count = couponRepository.count()

        // 쿠폰 개수 조히 로직을 레디스의 incr 명령어를 호출하도록 변경한다.
        // 즉 쿠폰 발급 전에 발급된 쿠폰의 개수를 1 증가하고 그 개수를 확인한다.
        val increment: Long? = couponCountRepository.increment()

        if (increment != null) {
            if (increment > 100) {
                return
            } else {
                log.info("쿠폰 잔고: [{}]", increment)
            }
        }

        // 쿠폰을 직접 DB에 생성하지 않고 카프카 토픽에 userId 를 전송한다.
//        couponRepository.save(Coupon(userId = userId))
        couponCreateProducer.create(userId)
    }


}