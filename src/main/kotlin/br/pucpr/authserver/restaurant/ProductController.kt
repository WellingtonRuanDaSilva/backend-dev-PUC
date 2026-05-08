package br.pucpr.authserver.restaurant

import br.pucpr.authserver.restaurant.dto.CreateProductRequest
import br.pucpr.authserver.restaurant.dto.ProductResponse
import br.pucpr.authserver.users.SortDir
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
@SecurityRequirement(name = "jwt-auth")
class ProductController(val service: ProductService) {

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    fun create(@Valid @RequestBody req: CreateProductRequest) =
        service.create(req.toProduct())
            .let { ProductResponse(it) }
            .let { ResponseEntity.status(HttpStatus.CREATED).body(it) }

    @GetMapping
    fun list(
        @RequestParam(required = false) name: String?,
        @RequestParam(required = false) sortDir: String?
    ): ResponseEntity<List<ProductResponse>> {
        val dir = SortDir.findOrNull(sortDir ?: "ASC") ?: SortDir.ASC
        return service.findAll(name, dir)
            .map { ProductResponse(it) }
            .let { ResponseEntity.ok(it) }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) =
        ProductResponse(service.findById(id)).let { ResponseEntity.ok(it) }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun update(@PathVariable id: Long, @Valid @RequestBody req: CreateProductRequest) =
        service.update(id, req.toProduct())
            .let { ProductResponse(it) }
            .let { ResponseEntity.ok(it) }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }
}