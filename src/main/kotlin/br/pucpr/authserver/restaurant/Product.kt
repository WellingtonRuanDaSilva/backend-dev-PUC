package br.pucpr.authserver.restaurant

import jakarta.persistence.*

@Entity
@Table(name = "Product")
class Product(
    @Id @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var price: Double
)