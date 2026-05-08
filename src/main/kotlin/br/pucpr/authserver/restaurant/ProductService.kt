package br.pucpr.authserver.restaurant

import br.pucpr.authserver.exception.NotFoundException
import br.pucpr.authserver.users.SortDir
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductService(val repository: ProductRepository) {
    private val log = LoggerFactory.getLogger(ProductService::class.java)

    fun create(product: Product): Product {
        log.info("Criando novo produto: ${product.name}")
        return repository.save(product)
    }

    fun findAll(name: String?, dir: SortDir): List<Product> {
        val sort = if (dir == SortDir.ASC) Sort.by("name").ascending() else Sort.by("name").descending()

        return if (name.isNullOrBlank()) {
            repository.findAll(sort)
        } else {
            repository.findByNameContainingIgnoreCase(name, sort)
        }
    }

    fun findById(id: Long): Product {
        return repository.findByIdOrNull(id) ?: run {
            log.warn("Falha na busca: Produto $id não encontrado.")
            throw NotFoundException("Produto $id não encontrado")
        }
    }

    fun update(id: Long, productDetails: Product): Product {
        val product = findById(id)
        product.name = productDetails.name
        product.price = productDetails.price

        val updated = repository.save(product)
        log.info("Produto $id atualizado com sucesso.")
        return updated
    }

    fun delete(id: Long) {
        val product = findById(id)
        repository.delete(product)
        log.info("Produto $id deletado com sucesso.")
    }
}