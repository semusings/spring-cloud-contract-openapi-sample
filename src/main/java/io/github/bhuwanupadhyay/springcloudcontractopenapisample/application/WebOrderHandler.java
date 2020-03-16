package io.github.bhuwanupadhyay.springcloudcontractopenapisample.application;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.print.Pageable;

@RestController
class WebOrderHandler implements OrderHandler {

    @Override
    public Mono<OrderResource> createOrder(CreateOrderCommand command) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<OrderResource>> updateOrder(UpdateOrderCommand command) {
        return null;
    }

    @Override
    public Flux<OrderResource> listOrder(OrderResource filters, Pageable pageable) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<OrderResource>> getOrder(String id) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteOrder(String id) {
        return null;
    }
}
