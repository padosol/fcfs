package com.fcfs.fcfs.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fcfs.fcfs.repository.CouponCountRepository
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.core.spec.style.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.http.MediaType

@SpringBootTest
@AutoConfigureMockMvc
class CouponControllerTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val couponCountRepository: CouponCountRepository
) : DescribeSpec({

    afterEach {
        couponCountRepository.deleteByKey("coupon-count")
    }

    describe("apply coupon should return 200 OK") {
        // given
        val request = CouponController.UserRequest(userId = 1L)
        val requestBody = objectMapper.writeValueAsString(request)

        // when & then
        mockMvc.perform(
            post("/coupon/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
            .andExpect(status().isOk)
    }


    describe("apply coupon with invalid request should return 400 Bad Request") {
        // given
        val invalidRequest = "{}" // Empty JSON object without userId
        val requestBody = objectMapper.writeValueAsString(invalidRequest)

        // when & then
        mockMvc.perform(
            post("/coupon/apply")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
            .andExpect(status().isBadRequest)
    }

})