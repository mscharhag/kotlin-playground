package com.mscharhag.ktorexample

class Storage {
    private val products = mutableMapOf<String, Product>()

    fun save(product: Product) {
        products[product.id] = product
    }

    fun getAll() = products.values.toList()
    fun get(id: String) = products[id]
    fun delete(id: String) = products.remove(id)
}