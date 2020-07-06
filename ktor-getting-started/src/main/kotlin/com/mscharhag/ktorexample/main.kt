package com.mscharhag.ktorexample

import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, 8080) {
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }
        routing {
            val productStorage = Storage()

            // add some test data
            productStorage.save(Product("123", "My product", "A nice description"))
            productStorage.save(Product("324", "My other product", "A better description"))

            product(productStorage)
        }
    }.start(wait = true)
}