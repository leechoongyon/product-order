package io.simple.productOrder.core.api.controller.example

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ExampleController() {
    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/example")
    fun example(
        @RequestParam message: String
    ): String {
        log.info("example request : {}", message)
        return message
    }
}