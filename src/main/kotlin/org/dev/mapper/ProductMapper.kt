package org.dev.mapper

import jakarta.enterprise.context.ApplicationScoped
import org.dev.dto.ProductDTO
import org.dev.model.Product

@ApplicationScoped
class ProductMapper {
    fun toEntity(dto: ProductDTO): Product {
        val p = Product()
        p.name = dto.name
        p.description = dto.description
        p.price = dto.price
        return p
    }
}