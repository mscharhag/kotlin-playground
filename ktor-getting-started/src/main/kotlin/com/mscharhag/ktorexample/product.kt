package com.mscharhag.ktorexample

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import java.util.*

class Product(
        val id: String,
        var name: String,
        var description: String
) {
    override fun toString(): String {
        return "Product(name='$name', description='$description', id='$id')"
    }
}

fun Route.product(storage: Storage) {

    route("/products") {

        // GET /products
        get {
            call.respond(storage.getAll())
        }

        // GET /products/{id}
        get("/{id}") {
            val id = call.parameters["id"]!!
            val product = storage.get(id)
            if (product != null) {
                call.respond(product)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }

        // POST /products
        post {
            val data = call.receive<Product>()
            val product = Product(
                    id = UUID.randomUUID().toString(),
                    name = data.name,
                    description = data.description
            )
            println("data: $data")
            println("product: $product")
            storage.save(product)
            call.response.headers.append("Location", "http://localhost:8080/products/${product.id}")
            call.respond(HttpStatusCode.Created)
        }

        // PUT /products/{id}
        put("/{id}") {
            val id = call.parameters["id"]!!
            println("id " + id)
            val product = storage.get(id)
            if (product == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                val data = call.receive<Product>()
                product.name = data.name
                product.description = data.description
                storage.save(product)
                call.respond(HttpStatusCode.OK)
            }
        }

        // DELETE /products/{id}
        delete("/{id}") {
            val id = call.parameters["id"]!!
            val product = storage.delete(id)
            if (product != null) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}