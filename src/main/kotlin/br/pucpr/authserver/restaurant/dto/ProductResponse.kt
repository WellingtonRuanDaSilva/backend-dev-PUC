package br.pucpr.authserver.restaurant.dto

import br.pucpr.authserver.restaurant.Product

data class ProductResponse(val id: Long, val name: String, val price: Double) {
    constructor(p: Product) : this(p.id!!, p.name, p.price)
}