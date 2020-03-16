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

    @PutMapping("orders/{orderId}")
    Mono<ResponseEntity<OrderResource>> updateOrder(@PathVariable("orderId") String orderId, @RequestBody UpdateOrderCommand command);

    @GetMapping("orders")
    Flux<OrderResource> listOrder(@RequestParam OrderResource filters, Pageable pageable);

    @GetMapping("orders/{orderId}")
    Mono<ResponseEntity<OrderResource>> getOrder(@PathVariable("orderId") String orderId);

    @DeleteMapping("orders/{orderId}")
    Mono<ResponseEntity<Object>> deleteOrder(@PathVariable("orderId") String orderId);

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
