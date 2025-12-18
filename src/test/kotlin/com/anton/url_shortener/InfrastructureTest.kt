package com.anton.url_shortener

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InfrastructureTest: BaseIntegrationTest() {

    @Test
    fun `connection to db is established`() {
        assertThat(postgres.isCreated).isTrue
        assertThat(postgres.isRunning).isTrue
    }

    @Test
    fun `connection to redis is established`() {
        assertThat(redis.isCreated).isTrue
        assertThat(redis.isRunning).isTrue
    }

}