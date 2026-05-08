package br.pucpr.authserver.restaurant.dto

import br.pucpr.authserver.restaurant.Product
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateProductRequest(
    @field:NotBlank val name: String?,
    @field:NotNull val price: Double?
) {
    fun toProduct() = Product(name = name!!, price = price!!)
}