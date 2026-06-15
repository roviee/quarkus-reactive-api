package org.dev

import io.quarkus.hibernate.reactive.panache.common.WithSession
import io.quarkus.hibernate.reactive.panache.common.WithTransaction
import io.quarkus.vertx.web.Body
import io.quarkus.vertx.web.Route
import io.quarkus.vertx.web.Param
import io.smallrye.mutiny.Uni
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.core.MediaType
import org.dev.dto.ProductDTO
import org.dev.dto.SuccessResp
import org.dev.service.ProductService

@ApplicationScoped
class ProductResource(
    private val productService: ProductService
) {

    @Route(
        methods = [Route.HttpMethod.GET],
        path = "/api/products",
        produces = [MediaType.APPLICATION_JSON]
    )
    @WithSession
    fun getAllProducts() : Uni<SuccessResp> {
        return productService.getAllProducts()
    }

    @Route(
        methods = [Route.HttpMethod.GET],
        path = "/api/products/:id",
        produces = [MediaType.APPLICATION_JSON]
    )
    @WithSession
    fun getProductById(@Param("id") id: String) : Uni<SuccessResp> {
        return productService.getProductById(id)
    }

    @Route(
        methods = [Route.HttpMethod.POST],
        path = "/api/products",
        consumes = [MediaType.APPLICATION_JSON],
        produces = [MediaType.APPLICATION_JSON]
    )
    @WithTransaction
    fun create(@Body product: ProductDTO) : Uni<SuccessResp> {
        return productService.createProduct(product)
    }

    @Route(
        methods = [Route.HttpMethod.PUT],
        path = "/api/products/:id",
        consumes = [MediaType.APPLICATION_JSON],
        produces = [MediaType.APPLICATION_JSON]
    )
    @WithTransaction
    fun update(@Param("id") id: String, @Body updatedProduct: ProductDTO) : Uni<SuccessResp> {
        return productService.updateProduct(id, updatedProduct)
    }

    @Route(
        methods = [Route.HttpMethod.DELETE],
        path = "/api/products/:id",
        produces = [MediaType.APPLICATION_JSON]
    )
    @WithTransaction
    fun delete(@Param("id") id: String) : Uni<SuccessResp> {
        return productService.deleteProduct(id)
    }
}