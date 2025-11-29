package com.anton.url_shortener.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@Table(name = "urls")
class UrlMapping(
    @Column(nullable = false)
    val longUrl: String,

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_seq_gen")
    @SequenceGenerator(
        name = "url_seq_gen",
        sequenceName = "urls_id_seq",
        initialValue = 10001,
        allocationSize = 1
    )
    val id: Long = 0,

    @Column(nullable = true, unique = true)
    val alias: String? = null,

    @Column(nullable = false, columnDefinition = "integer default 0")
    var clickCount: Int = 0
)