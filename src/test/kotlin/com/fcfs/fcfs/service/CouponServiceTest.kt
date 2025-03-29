package com.fcfs.fcfs.service

import com.fcfs.fcfs.repository.CouponCountRepository
import com.fcfs.fcfs.repository.CouponRepository
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@SpringBootTest
@TestPropertySource(locations = ["classpath:application-test.yml"])
class CouponServiceTest(

    @Autowired
    private val couponService: CouponService,

    @Autowired
    private val couponRepository: CouponRepository,

    @Autowired
    private val couponCountRepository: CouponCountRepository

) : BehaviorSpec({

    afterEach {
        couponRepository.deleteAll()
        couponCountRepository.deleteByKey("coupon-count")
    }

    Given("유저가 응모를 하면") {
        couponService.apply(1)
        When("카운트가 1 증가한다.") {
            val count = couponRepository.count()
            Then("count 는 1이다.") {
                count shouldBe 1
            }
        }
    }


    Given("1000명의 유저가 동시에 100개 한정 쿠폰에 응모할 때") {
        // 테스트 설정
        val totalUserCount = 1000
        val availableCouponCount = 100
        val threadPoolSize = 32

        // 동시성 처리를 위한 설정
        val executorService = Executors.newFixedThreadPool(threadPoolSize)
        val completionLatch = CountDownLatch(totalUserCount)

        // 1000명의 사용자가 동시에 쿠폰 응모
        for (userId in 1L..totalUserCount) {
            executorService.execute {
                try {
                    // 쿠폰 응모 요청
                    couponService.apply(userId)
                } finally {
                    // 작업 완료 표시
                    completionLatch.countDown()
                }
            }
        }

        // 모든 요청이 완료될 때까지 대기
        completionLatch.await()

        When("모든 응모 요청이 처리된 후") {
            // 실제 발급된 쿠폰 수 확인
            val issuedCouponCount = couponRepository.count()

            Then("정확히 100개의 쿠폰만 발급되어야 함") {
                issuedCouponCount shouldBe availableCouponCount
            }
        }
    }

})
