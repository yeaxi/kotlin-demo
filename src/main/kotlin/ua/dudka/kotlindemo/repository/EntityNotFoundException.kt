package ua.dudka.kotlindemo.repository

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity with such id does not exist")
class EntityNotFoundException : RuntimeException()