package io.simple.productOrder.infrastructure.product

import io.simple.productOrder.domain.product.ProductCommand
import io.simple.productOrder.domain.product.ProductExecutor
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProductExecutorImpl(private val productRepository: ProductRepository) : ProductExecutor {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun reduceStock(reduceProduct: ProductCommand.ReduceProduct) {
        log.info("reduceStock... {}", reduceProduct)

        productRepository.findById(reduceProduct.getProductId())
            .switchIfEmpty(Mono.error(IllegalArgumentException("Product not found: ${reduceProduct.getProductId()}")))
            .flatMap { product ->
                if (product.getStock() < reduceProduct.getQuantity()) {
                    Mono.error(IllegalArgumentException("Insufficient stock for product ${product.getId()}"))
                } else {
                    productRepository.reduceStock(product.getId(), reduceProduct.getQuantity())
                        .filter { rowsUpdated -> rowsUpdated == 1 }
                        .switchIfEmpty(Mono.error(IllegalStateException("Failed to reduce stock for product ${product.getId()}")))
                        .thenReturn(product)
                }
            }
            .subscribe()
    }
}