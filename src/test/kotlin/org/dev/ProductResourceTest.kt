package org.dev

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test

@QuarkusTest
class ProductResourceTest {

    @Test
    fun shouldReturnProducts() {
        given()
            .`when`()
            .get("/api/products")
            .then()
            .statusCode(200)
            .body("[0].id", equalTo(101))
            .body("[0].name", equalTo("Quarkus Mug"))
            .body("[0].description", equalTo("Ceramic mug with Quarkus logo"))
            .body("[0].price", equalTo(12.99f))
    }
}