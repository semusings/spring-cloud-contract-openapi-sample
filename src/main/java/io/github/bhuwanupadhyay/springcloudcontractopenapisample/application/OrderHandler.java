package io.github.bhuwanupadhyay.springcloudcontractopenapisample.application;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.print.Pageable;

public interface OrderHandler {

    @PostMapping("orders")
    Mono<OrderResource> createOrder(@RequestBody CreateOrderCommand command);

    @PutMapping("orders/{id}")
    Mono<ResponseEntity<OrderResource>> updateOrder(@RequestBody UpdateOrderCommand command);

    @GetMapping("orders")
    Flux<OrderResource> listOrder(@RequestParam OrderResource filters, Pageable pageable);

    @GetMapping("orders/{id}")
    Mono<ResponseEntity<OrderResource>> getOrder(@PathVariable("id") String id);

    @DeleteMapping("orders/{id}")
    Mono<ResponseEntity<Void>> deleteOrder(@PathVariable("id") String id);

    record ErrorResource(@JsonProperty("errorId")String errorId,
                         @JsonProperty("message")String translations) {
    }

    record UpdateOrderCommand(
            @JsonProperty("itemName")String itemName,
            @JsonProperty("quantity")Integer quantity) {

    }

    record CreateOrderCommand(@JsonProperty("customerId")String customerId,
                              @JsonProperty("itemName")String itemName,
                              @JsonProperty("quantity")Integer quantity) {

    }

    record OrderResource(@JsonProperty("orderId")String orderId,
                         @JsonProperty("customerId")String customerId,
                         @JsonProperty("itemName")String itemName,
                         @JsonProperty("quantity")Integer quantity) {

    }

}
