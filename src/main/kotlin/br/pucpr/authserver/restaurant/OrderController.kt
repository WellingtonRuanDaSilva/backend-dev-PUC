package br.pucpr.authserver.restaurant

import br.pucpr.authserver.restaurant.dto.CreateOrderRequest
import br.pucpr.authserver.restaurant.dto.OrderResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
@SecurityRequirement(name = "jwt-auth")
class OrderController(val service: OrderService) {

    @PostMapping
    fun create(@Valid @RequestBody req: CreateOrderRequest) =
        service.createOrder(req.clientName!!)
            .let { OrderResponse(it) }
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) =
        OrderResponse(service.getOrder(id)).let { ResponseEntity.ok(it) }

    @PostMapping("/{id}/products/{productId}")
    fun addProduct(@PathVariable id: Long, @PathVariable productId: Long) =
        service.addProduct(id, productId)
            .let { OrderResponse(it) }
            .let { ResponseEntity.ok(it) }

    @DeleteMapping("/{id}/products/{productId}")
    fun removeProduct(@PathVariable id: Long, @PathVariable productId: Long) =
        service.removeProduct(id, productId)
            .let { OrderResponse(it) }
            .let { ResponseEntity.ok(it) }
}