package com.anton.url_shortener.repository

import com.anton.url_shortener.model.UrlMapping
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UrlRepository: JpaRepository<UrlMapping, Long> { // <EntityClass, ID_Type>
    fun existsByAlias(alias: String?): Boolean
    fun findByAlias(alias: String): Optional<UrlMapping>
}