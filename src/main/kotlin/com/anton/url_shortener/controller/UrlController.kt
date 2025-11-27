package com.anton.url_shortener.controller

import com.anton.url_shortener.dto.ShortenRequest
import com.anton.url_shortener.logic.Base62Encoder
import com.anton.url_shortener.model.UrlMapping
import com.anton.url_shortener.repository.UrlRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api")
class UrlController(private val encoder: Base62Encoder, private val repository: UrlRepository) {

    @Operation(summary = "Shorten a URL", description = "Takes a long URL and returns a short link")
    @ApiResponse(responseCode = "200", description = "Successfully shortened")
    @PostMapping("/shorten")
    fun shorten(@RequestBody request: ShortenRequest): String {
        val newMapping = UrlMapping(longUrl = request.url)
        val savedMapping = repository.save(newMapping)
        val id = savedMapping.id
        val shortString = encoder.encode(id)
        return "http://localhost:8080/api/$shortString"
    }

    @Operation(summary = "Redirect to original", description = "Finds the original URL by ID and redirects the browser")
    @ApiResponse(responseCode = "302", description = "Found and redirecting")
    @ApiResponse(responseCode = "404", description = "Short code not found")
    @GetMapping("/{shortCode}")
    fun redirect(@PathVariable shortCode: String): ResponseEntity<Void> {
        val decodedId = encoder.decode(shortCode)
        val mapping = repository.findById(decodedId)
        val entity = mapping.orElse(null)
        return entity?.let {
            ResponseEntity.status(HttpStatus.FOUND).location(URI.create(it.longUrl)).build()
        } ?: ResponseEntity.status(HttpStatus.NOT_FOUND).build()

    }

}
