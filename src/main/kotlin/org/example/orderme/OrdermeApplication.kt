package org.example.orderme

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class OrdermeApplication

fun main(args: Array<String>) {
    runApplication<OrdermeApplication>(*args)
}
