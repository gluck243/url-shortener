package com.anton.url_shortener.repository

import com.anton.url_shortener.integration.BaseIntegrationTest
import com.anton.url_shortener.model.UrlMapping
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class UrlRepositoryTest: BaseIntegrationTest() {

    @Autowired
    private lateinit var urlRepository: UrlRepository

    @Test
    fun `should save and retrieve url by alias`() {
        val newUrl = UrlMapping("https://www.google.com", alias = "gugl");
        val savedUrl = urlRepository.save(newUrl)

        assertThat(savedUrl.id).isNotNull

        assertThat(urlRepository.existsByAlias("gugl")).isTrue

        val foundUrl = urlRepository.findByAlias("gugl")
        assertThat(foundUrl).isPresent
        assertThat(foundUrl.get().longUrl).isEqualTo("https://www.google.com")
    }

}