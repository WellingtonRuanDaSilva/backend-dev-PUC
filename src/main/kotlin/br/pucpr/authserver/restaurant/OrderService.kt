package br.pucpr.authserver.restaurant

import br.pucpr.authserver.exception.NotFoundException
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class OrderService(
    val orderRepository: OrderRepository,
    val productService: ProductService
) {
    private val log = LoggerFactory.getLogger(OrderService::class.java)

    fun createOrder(clientName: String): Order {
        log.info("Criando novo pedido para o cliente: $clientName")
        return orderRepository.save(Order(clientName = clientName))
    }

    fun getOrder(id: Long): Order {
        return orderRepository.findByIdOrNull(id) ?: run {
            log.warn("Falha na busca: Pedido $id não encontrado.")
            throw NotFoundException("Pedido $id não encontrado")
        }
    }

    fun addProduct(orderId: Long, productId: Long): Order {
        val order = getOrder(orderId)
        val product = productService.findById(productId)

        order.products.add(product)
        val savedOrder = orderRepository.save(order)
        log.info("Produto $productId adicionado ao pedido $orderId.")

        return savedOrder
    }

    fun removeProduct(orderId: Long, productId: Long): Order {
        val order = getOrder(orderId)
        val product = productService.findById(productId)

        order.products.remove(product)
        val savedOrder = orderRepository.save(order)
        log.info("Produto $productId removido do pedido $orderId.")

        return savedOrder
    }
}