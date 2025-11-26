package com.anton.url_shortener.controller

import com.anton.url_shortener.dto.ShortenRequest
import com.anton.url_shortener.logic.Base62Encoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
@RequestMapping("/api")
class UrlController {
    private val encoder = Base62Encoder()

    private val db = HashMap<Long, String>()

    private val idSequence = AtomicLong(100)

    @PostMapping("/shorten")
    fun shorten(@RequestBody request: ShortenRequest): String {
        val id = idSequence.getAndIncrement()
        db[id] = request.url
        val shortString = encoder.encode(id)
        return "http://localhost:8080/api/$shortString"
    }
}
