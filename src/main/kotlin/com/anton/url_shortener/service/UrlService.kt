package com.anton.url_shortener.service

import com.anton.url_shortener.logic.Base62Encoder
import com.anton.url_shortener.model.UrlMapping
import com.anton.url_shortener.repository.UrlRepository
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class UrlService(private val encoder: Base62Encoder,
                 private val repository: UrlRepository,
                 private val redis: StringRedisTemplate
) {
    fun shorten(originalUrl: String): String {
        val newMapping = UrlMapping(longUrl = originalUrl)
        val savedMapping = repository.save(newMapping)
        val id = savedMapping.id
        return encoder.encode(id)
    }

    fun getOriginalUrl(shortCode: String): String? {
        val cached = redis.opsForValue().get(shortCode)
        if (cached != null) {
            println("Cache Hit! Returning from RAM.")
            return cached
        }
        val decodedId = encoder.decode(shortCode)
        val entity = repository.findById(decodedId).orElse(null) ?: return null
        redis.opsForValue().set(shortCode, entity.longUrl, 10, TimeUnit.MINUTES)
        return entity.longUrl
    }
}