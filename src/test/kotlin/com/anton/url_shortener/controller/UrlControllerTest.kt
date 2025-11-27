package com.anton.url_shortener.controller

import com.anton.url_shortener.logic.Base62Encoder
import com.anton.url_shortener.model.UrlMapping
import com.anton.url_shortener.repository.UrlRepository
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.mockito.ArgumentMatchers.any
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
    private lateinit var urlRepository: UrlRepository

    @MockkBean
    private lateinit var encoder: Base62Encoder

    @Test
    fun `should shorten url successfully`() {

        // Given (Setup the mocks)
        val originalUrl = "https://google.com"
        val fakeId = 123L
        val expectedShortCode = "bc"

        every { urlRepository.save(any()) } returns UrlMapping(id = fakeId, longUrl = originalUrl)

        every { encoder.encode(fakeId) } returns expectedShortCode

        // When (Perform the request)
        mockMvc.perform(
            post("/api/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"url": "$originalUrl"}""")
        )

            // THEN (Verify the response)
            .andExpect(status().isOk)
            .andExpect(content().string("http://localhost:8080/api/$expectedShortCode"))

        // Verify that the save method was actually called
        verify(exactly = 1) {urlRepository.save(any())}
    }
}