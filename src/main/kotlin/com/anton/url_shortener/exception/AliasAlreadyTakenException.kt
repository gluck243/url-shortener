package com.anton.url_shortener.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class AliasAlreadyTakenException(message: String): RuntimeException(message) {
}