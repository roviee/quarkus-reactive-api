package org.dev.repository

import io.quarkus.hibernate.reactive.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import org.dev.model.Product

@ApplicationScoped
class ProductRepository : PanacheRepository<Product> {
}