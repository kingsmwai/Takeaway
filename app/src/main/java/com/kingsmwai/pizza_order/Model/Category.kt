package com.kingsmwai.pizza_order.Model

class Category() {

    var name: String? = null
    var image: String? = null

    constructor(name: String, image: String) : this() {
        this.name = name
        this.image = image
    }
}
