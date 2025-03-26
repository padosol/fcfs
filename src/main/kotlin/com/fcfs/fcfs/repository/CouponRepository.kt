package com.fcfs.fcfs.repository

import com.fcfs.fcfs.entity.Coupon
import org.springframework.data.jpa.repository.JpaRepository

interface CouponRepository : JpaRepository<Coupon, Long> {
}