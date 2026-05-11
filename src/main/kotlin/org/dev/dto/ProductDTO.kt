package org.dev.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class ProductDTO(
    val id: Long? = null,
    val name: String,
    val description: String? = null,
    val price: Double
)
