package br.pucpr.authserver.restaurant

import jakarta.persistence.*

@Entity
@Table(name = "RestaurantOrder")
class Order(
    @Id @GeneratedValue
    var id: Long? = null,

    @Column(nullable = false)
    var clientName: String,

    @ManyToMany
    @JoinTable(
        name = "OrderProduct",
        joinColumns = [JoinColumn(name = "idOrder")],
        inverseJoinColumns = [JoinColumn(name = "idProduct")]
    )
    var products: MutableSet<Product> = mutableSetOf()
)