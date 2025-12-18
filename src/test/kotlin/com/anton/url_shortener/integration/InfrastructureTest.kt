package com.anton.url_shortener.integration

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class InfrastructureTest: BaseIntegrationTest() {

    @Test
    fun `connection to db is established`() {
        Assertions.assertThat(postgres.isCreated).isTrue
        Assertions.assertThat(postgres.isRunning).isTrue
    }

    @Test
    fun `connection to redis is established`() {
        Assertions.assertThat(redis.isCreated).isTrue
        Assertions.assertThat(redis.isRunning).isTrue
    }

}