package org.dev.service

import io.smallrye.mutiny.Uni
import org.dev.dto.SuccessResp
import org.dev.model.Product

interface ProductService {
    fun getAllProducts(): Uni<SuccessResp>
    fun createProduct(product: Product): Uni<SuccessResp>
    fun getProductById(id: String): Uni<SuccessResp>
    fun updateProduct(id: String, productDTO: Product): Uni<SuccessResp>
    fun deleteProduct(id: String): Uni<SuccessResp>
}