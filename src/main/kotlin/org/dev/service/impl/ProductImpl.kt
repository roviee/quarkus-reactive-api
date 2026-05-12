package org.dev.service.impl

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.NotFoundException
import org.dev.dto.ProductDTO
import org.dev.dto.SuccessResp
import org.dev.model.Product
import org.dev.repository.ProductRepository
import org.dev.service.ProductService

@ApplicationScoped
class ProductImpl(
    val productRepository: ProductRepository
) : ProductService {
    override fun getAllProducts() : Uni<SuccessResp> {
        return productRepository.listAll()
            .onItem().transform { products ->
                val dtoList = products.map{ product ->
                    ProductDTO(
                        id = product.id,
                        name = product.name,
                        description = product.description,
                        price = product.price
                    )
                }
                SuccessResp(dtoList)
            }
    }

    override fun createProduct(product: Product): Uni<SuccessResp> {
        return productRepository.persist(product)
            .onItem().transform {
                SuccessResp(
                ProductDTO(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    price = it.price
                )
            ) }
    }

    override fun getProductById(id: String): Uni<SuccessResp> {
        return productRepository.findById(id.toLong())
            .onItem().transform { product ->
                SuccessResp(
                    ProductDTO(
                        id = product.id,
                        name = product.name,
                        description = product.description,
                        price = product.price
                    )
                )
            }
    }

    fun findProduct(id: String): Uni<Product> {
        return productRepository.findById(id.toLong())
            .onItem().ifNull().failWith { NotFoundException("Product not found") }
    }

    override fun updateProduct(
        id: String,
        productDTO: Product
    ): Uni<SuccessResp> {
        return findProduct(id)
            .onItem().transformToUni { existingProduct ->
                existingProduct.name = productDTO.name
                existingProduct.description = productDTO.description
                existingProduct.price = productDTO.price

                productRepository.persist(existingProduct)
            }
            .onItem().transform { savedProduct ->
                SuccessResp(ProductDTO(
                    id = savedProduct.id,
                    name = savedProduct.name,
                    description = savedProduct.description,
                    price = savedProduct.price
                ))
            }
    }

    override fun deleteProduct(id: String): Uni<SuccessResp> {
        return findProduct(id)
            .onItem().transformToUni { existingProduct ->
                productRepository.delete(existingProduct)
                    .onItem().transform { SuccessResp("product deleted $id") }
            }
    }
}