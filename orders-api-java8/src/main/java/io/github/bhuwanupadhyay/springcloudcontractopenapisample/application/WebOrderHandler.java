package io.github.bhuwanupadhyay.springcloudcontractopenapisample.application;

import io.github.bhuwanupadhyay.springcloudcontractopenapisample.domain.OrderEntity;
import io.github.bhuwanupadhyay.springcloudcontractopenapisample.domain.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class WebOrderHandler implements OrderHandler {

    private final AtomicInteger orderIdGen = new AtomicInteger(1000);
    private final OrderRepository orderRepository;

    WebOrderHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Mono<OrderResource> createOrder(CreateOrderCommand command) {
        return Mono.justOrEmpty(
                toResource(
                        orderRepository.save(
                                new OrderEntity(
                                        String.valueOf(orderIdGen.incrementAndGet()),
                                        command.getCustomerId(),
                                        command.getItemName(),
                                        command.getQuantity()
                                )
                        )
                )
        );
    }

    @Override
    public Mono<ResponseEntity<OrderResource>> updateOrder(String orderId,
                                                           UpdateOrderCommand command) {
        ResponseEntity<OrderResource> result = orderRepository.find(orderId)
                .map(entity -> orderRepository.update(orderId, toEntity(command, entity)))
                .map(entity -> ResponseEntity.status(HttpStatus.OK).body(toResource(entity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
        return Mono.justOrEmpty(result);
    }

    @Override
    public Mono<Page<OrderResource>> listOrder(OrderResource filters, Pageable pageable) {
        OrderEntity entity = new OrderEntity(
                filters.getOrderId(),
                filters.getCustomerId(),
                filters.getItemName(),
                filters.getQuantity()
        );
        return Mono.justOrEmpty(orderRepository.list(entity, pageable).map(this::toResource));
    }

    @Override
    public Mono<ResponseEntity<OrderResource>> getOrder(String orderId) {
        ResponseEntity<OrderResource> result = orderRepository.find(orderId)
                .map(entity -> ResponseEntity.status(HttpStatus.OK).body(toResource(entity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
        return Mono.justOrEmpty(result);
    }

    @Override
    public Mono<ResponseEntity<Object>> deleteOrder(String orderId) {
        return Mono.justOrEmpty(
                orderRepository.find(orderId)
                        .map(entity -> {
                            orderRepository.delete(orderId);
                            return ResponseEntity.status(HttpStatus.OK).build();
                        })
                        .orElseGet(() -> ResponseEntity.notFound().build())
        );
    }

    private OrderResource toResource(OrderEntity entity) {
        return new OrderResource(
                entity.getOrderId(),
                entity.getCustomerId(),
                entity.getItemName(),
                entity.getQuantity()
        );
    }

    private OrderEntity toEntity(UpdateOrderCommand command, OrderEntity entity) {
        return new OrderEntity(entity.getOrderId(), entity.getCustomerId(), command.getItemName(), command.getQuantity());
    }

}
