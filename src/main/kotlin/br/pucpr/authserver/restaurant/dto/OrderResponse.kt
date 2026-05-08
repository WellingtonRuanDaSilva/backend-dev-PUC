package br.pucpr.authserver.restaurant.dto

import br.pucpr.authserver.restaurant.Order

data class OrderResponse(val id: Long, val clientName: String, val products: List<ProductResponse>) {
    constructor(o: Order) : this(
        o.id!!,
        o.clientName,
        o.products.map { ProductResponse(it) }
    )
}