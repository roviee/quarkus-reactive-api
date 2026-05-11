package org.dev

import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.DELETE
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.core.Response
import org.dev.dto.ProductDTO
import org.dev.model.Product
import org.dev.repository.ProductRepository

@ApplicationScoped
class ProductResource(
    val productRepository: ProductRepository
) {

    @POST
    @WithTransaction
    fun create(product: Product) : Uni<Response> {
        return productRepository.persist(product)
            .onItem().transform { Response.ok(it).status(Response.Status.CREATED).build() }
    }

    @GET
    @Path("/{id}")
    @WithSession
    fun getById(@PathParam("id") id: Long) : Uni<Response> {
        return productRepository.findById(id)
            .onItem().ifNotNull().transform { product -> Response.ok(product).build() }
            .onItem().ifNull().continueWith { Response.status(Response.Status.NOT_FOUND).build() }
    }

    @PUT
    @Path("/{id}")
    @WithTransaction
    fun update(@PathParam("id") id: Long, updatedProduct: ProductDTO) : Uni<Response> {
        return productRepository.findById(id)
            .onItem().ifNotNull().transformToUni { existingProduct ->
                existingProduct.name = updatedProduct.name
                existingProduct.description = updatedProduct.description
                existingProduct.price = updatedProduct.price
                productRepository.persist(existingProduct)
                    .onItem().transform { Response.ok(it).build() }
            }
            .onItem().ifNull().continueWith(Response.status(Response.Status.NOT_FOUND)::build);
    }

    @DELETE
    @Path("/{id}")
    @WithTransaction
    fun delete(@PathParam("id") id: Long) : Uni<Response> {
        return productRepository.findById(id)
            .onItem().ifNotNull().transformToUni { existingProduct ->
                productRepository.delete(existingProduct)
                    .onItem().transform { Response.noContent().build() }
            }
    }
}