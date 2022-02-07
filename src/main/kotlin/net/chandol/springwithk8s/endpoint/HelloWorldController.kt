package net.chandol.springwithk8s.endpoint

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {
    @GetMapping("/api/v1/hello-world")
    suspend fun helloWorld() = "hello world!"
}
