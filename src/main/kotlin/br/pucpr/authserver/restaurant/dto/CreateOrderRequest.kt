package br.pucpr.authserver.restaurant.dto

import jakarta.validation.constraints.NotBlank

data class CreateOrderRequest(
    @field:NotBlank val clientName: String?
)