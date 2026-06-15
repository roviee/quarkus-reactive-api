package org.dev.service.impl

import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.NotFoundException
import org.dev.dto.ProductDTO
import org.dev.dto.SuccessResp
import org.dev.mapper.ProductMapper
import org.dev.model.Product
import org.dev.repository.ProductRepository
import org.dev.service.ProductService

@ApplicationScoped
class ProductImpl(
    val productRepository: ProductRepository,
    val productMapper: ProductMapper
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

    override fun createProduct(product: ProductDTO): Uni<SuccessResp> {
        val entity = productMapper.toEntity(product)

        return productRepository.persist(entity)
            .onItem().transform {
                SuccessResp(
                ProductDTO(
                    id = entity.id,
                    name = entity.name,
                    description = entity.description,
                    price = entity.price
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
        product: ProductDTO
    ): Uni<SuccessResp> {
        return findProduct(id)
            .onItem().transformToUni { existingProduct ->
                existingProduct.name = product.name
                existingProduct.description = product.description
                existingProduct.price = product.price

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