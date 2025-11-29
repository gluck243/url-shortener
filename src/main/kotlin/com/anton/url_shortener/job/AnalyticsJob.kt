package com.anton.url_shortener.job

import com.anton.url_shortener.logic.Base62Encoder
import com.anton.url_shortener.repository.UrlRepository
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class AnalyticsJob(private val repository: UrlRepository, private val redis: StringRedisTemplate, private val encoder: Base62Encoder) {

    @Scheduled(fixedRate = 60000)
    fun analyse() {
        redis.opsForSet().pop("sync:updates", 1000)?.forEach { shortCode ->
            val clicks = redis.opsForValue().getAndDelete("clicks:$shortCode")
            clicks?.let { count ->
                val id = encoder.decode(shortCode)
                val mapping = repository.findById(id).orElse(null)
                if (mapping != null) {
                    mapping.clickCount += count.toInt()
                    repository.save(mapping)
                }
            }
        } ?: return

    }

}