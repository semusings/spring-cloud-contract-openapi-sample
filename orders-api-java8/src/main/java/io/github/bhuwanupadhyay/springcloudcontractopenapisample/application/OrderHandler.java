package io.github.bhuwanupadhyay.springcloudcontractopenapisample.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


public interface OrderHandler {

    @PostMapping("orders")
    Mono<OrderResource> createOrder(@RequestBody CreateOrderCommand command);

    @PutMapping("orders/{orderId}")
    Mono<ResponseEntity<OrderResource>> updateOrder(@PathVariable("orderId") String orderId, @RequestBody UpdateOrderCommand command);

    @GetMapping("orders")
    Mono<Page<OrderResource>> listOrder(@RequestParam OrderResource filters, Pageable pageable);

    @GetMapping("orders/{orderId}")
    Mono<ResponseEntity<OrderResource>> getOrder(@PathVariable("orderId") String orderId);

    @DeleteMapping("orders/{orderId}")
    Mono<ResponseEntity<Object>> deleteOrder(@PathVariable("orderId") String orderId);

    @Data
    @AllArgsConstructor
    class OrderResource {
        private String orderId;
        private String customerId;
        private String itemName;
        private Integer quantity;
    }

    @Data
    class CreateOrderCommand {
        private String customerId;
        private String itemName;
        private Integer quantity;
    }

    @Data
    @AllArgsConstructor
    class ErrorResource {
        private String errorId;
        private String translations;
    }

    @Data
    class UpdateOrderCommand {
        private String itemName;
        private Integer quantity;

    }


}
