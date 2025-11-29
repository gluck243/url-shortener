package com.anton.url_shortener.service

import com.anton.url_shortener.exception.AliasAlreadyTakenException
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
    fun shorten(originalUrl: String, alias: String?): String {
        if (alias != null && repository.existsByAlias(alias)) {
            throw AliasAlreadyTakenException("$alias is already taken for $originalUrl." +
                    " Please choose a different alias and try again.")
        }
        val newMapping = UrlMapping(longUrl = originalUrl, alias = alias)
        val savedMapping = repository.save(newMapping)
        val id = savedMapping.id
        return alias ?: encoder.encode(id)
    }

    fun getOriginalUrl(shortCode: String): String? {
        var url = redis.opsForValue().get(shortCode)
        if (url == null) {
            val byAlias = repository.findByAlias(shortCode).orElse(null)
            if (byAlias != null)
                url = byAlias.longUrl
            else {
                try {
                    val id = encoder.decode(shortCode)
                    val byId = repository.findById(id).orElse(null)
                    if (byId != null && encoder.encode(id) == shortCode)
                        url = byId.longUrl
                } catch (e: Exception) {
                    return null
                }
            }
        }

        if (url != null) {
            redis.opsForValue().set(shortCode, url, 10, TimeUnit.MINUTES)
            redis.opsForValue().increment("clicks:$shortCode")
            redis.opsForSet().add("sync:updates", shortCode)
        }

        return url
    }

//    fun checkIfExists(longUrl: String) {
//        repository.findByLongUrl()
//    }
}