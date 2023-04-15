package io.simple.productOrder.infrastructure.product

import io.simple.productOrder.domain.product.Product
import io.simple.productOrder.domain.product.ProductCommand
import io.simple.productOrder.domain.product.ProductExecutor
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class ProductExecutorImpl(private val productRepository: ProductRepository) : ProductExecutor {
    override fun reduceStock(reduceProduct: ProductCommand.ReduceProduct): Mono<Product> {
        return productRepository.findById(reduceProduct.getProductId())
            .switchIfEmpty(Mono.error(IllegalArgumentException("Product not found: ${reduceProduct.getProductId()}")))
            .flatMap { product ->
                validateStock(product, reduceProduct.getQuantity())
                    .flatMap {
                        productRepository.reduceStock(product.getId(), reduceProduct.getQuantity())
                            .filter { rowsUpdated -> rowsUpdated == 1 }
                            .switchIfEmpty(Mono.error(IllegalStateException("Failed to reduce stock for product ${product.getId()}")))
                            .thenReturn(product)
                    }
            }
            .onErrorResume { e ->
                // 예외 처리 로직
                Mono.error(e)
            }
    }

    private fun validateStock(product: Product, quantity: Int): Mono<Product> {
        if (product.getStock() < quantity) {
            return Mono.error(IllegalArgumentException("Insufficient stock for product ${product.getId()}"))
        }
        return Mono.just(product)
    }
}