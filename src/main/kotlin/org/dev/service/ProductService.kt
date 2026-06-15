package org.dev.service

import io.smallrye.mutiny.Uni
import org.dev.dto.ProductDTO
import org.dev.dto.SuccessResp

interface ProductService {
    fun getAllProducts(): Uni<SuccessResp>
    fun createProduct(product: ProductDTO): Uni<SuccessResp>
    fun getProductById(id: String): Uni<SuccessResp>
    fun updateProduct(id: String, product: ProductDTO): Uni<SuccessResp>
    fun deleteProduct(id: String): Uni<SuccessResp>
}