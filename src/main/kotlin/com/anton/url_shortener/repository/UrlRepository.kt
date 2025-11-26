package com.anton.url_shortener.repository

import com.anton.url_shortener.model.UrlMapping
import org.springframework.data.jpa.repository.JpaRepository

interface UrlRepository: JpaRepository<UrlMapping, Long> { // <EntityClass, ID_Type>
}