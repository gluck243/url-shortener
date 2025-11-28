package com.anton.url_shortener.controller

import com.anton.url_shortener.service.UrlService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import kotlin.test.Test

@WebMvcTest(UrlController::class)
class UrlControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var service: UrlService

    @Test
    fun `should shorten url successfully`() {

        // Given (Setup the mocks)
        val originalUrl = "https://google.com"
        val expectedShortCode = "bc"

        every {service.shorten(originalUrl)} returns expectedShortCode

        // When (Perform the request)
        mockMvc.perform(
            post("/api/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"url": "$originalUrl"}""")
        )

            // THEN (Verify the response)
            .andExpect(status().isOk)
            .andExpect(content().string("http://localhost:8080/api/$expectedShortCode"))
    }
}