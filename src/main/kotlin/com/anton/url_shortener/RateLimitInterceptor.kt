package com.anton.url_shortener

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.concurrent.TimeUnit

@Component
class RateLimitInterceptor(private val redis: StringRedisTemplate): HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val ip = request.getHeader("X-Forwarded-For") ?: request.remoteAddr
        val key = "rate_limit:$ip"
        val count = redis.opsForValue().increment(key) ?: 1L
        if (count == 1L) {
            redis.expire(key, 1, TimeUnit.MINUTES)
        }
        else if (count > 10) {
            response.status = 429
            response.writer.write("Too many requests")
            return false
        }
        return true

    }
}