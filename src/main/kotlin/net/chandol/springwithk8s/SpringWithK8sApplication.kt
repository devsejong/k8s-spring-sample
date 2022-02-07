package net.chandol.springwithk8s

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
class SpringWithK8sApplication

fun main(args: Array<String>) {
    runApplication<SpringWithK8sApplication>(*args)
}
