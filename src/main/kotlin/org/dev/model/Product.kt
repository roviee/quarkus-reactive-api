package org.dev.model

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity

@Entity
class Product : PanacheEntity() {
    lateinit var name: String
     var description: String? = null
    var price: Double = 0.0
}