package com.fcfs.fcfs.controller

import com.fcfs.fcfs.service.CouponService
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/coupon")
class CouponController(
    private val couponService: CouponService
) {
    private val log: Logger = LoggerFactory.getLogger(CouponController::class.java)
    @PostMapping("/apply")
    fun applyCoupon(@RequestBody request: UserRequest) {
        log.info("apply coupon request by userId = {}", request.userId);
        couponService.apply(request.userId);
    }

    @GetMapping("/k6")
    fun k6Test(): String {
        return "ok"
    }

    data class UserRequest(
        val userId: Long
    )
}

