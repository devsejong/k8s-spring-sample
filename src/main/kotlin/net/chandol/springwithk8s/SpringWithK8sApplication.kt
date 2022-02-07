package net.chandol.springwithk8s

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringWithK8sApplication

fun main(args: Array<String>) {
    runApplication<SpringWithK8sApplication>(*args)
}
