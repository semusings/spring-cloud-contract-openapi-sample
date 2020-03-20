package io.github.bhuwanupadhyay.ordersapijava8.application;

import io.github.bhuwanupadhyay.ordersapijava8.domain.OrderEntity;
import io.github.bhuwanupadhyay.ordersapijava8.domain.OrderRepository;
import io.github.bhuwanupadhyay.ordersapijava8.openapi.CreateOrderCommand;
import io.github.bhuwanupadhyay.ordersapijava8.openapi.OrderResource;
import io.github.bhuwanupadhyay.ordersapijava8.openapi.OrdersApi;
import io.github.bhuwanupadhyay.ordersapijava8.openapi.UpdateOrderCommand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class WebOrderHandler implements OrdersApi {

    private final AtomicInteger orderIdGen = new AtomicInteger(1000);
    private final OrderRepository orderRepository;

    WebOrderHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public ResponseEntity<OrderResource> createOrder(CreateOrderCommand command) {
        return ResponseEntity.ok().body(
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
    public ResponseEntity<OrderResource> updateOrder(String orderId,
                                                     UpdateOrderCommand command) {
        return orderRepository.find(orderId)
                .map(entity -> orderRepository.update(orderId, toEntity(command, entity)))
                .map(entity -> ResponseEntity.status(HttpStatus.OK).body(toResource(entity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Page> listOrder(OrderResource filters, Pageable pageable) {
        OrderEntity entity = new OrderEntity(
                filters.getOrderId(),
                filters.getCustomerId(),
                filters.getItemName(),
                filters.getQuantity()
        );
        return ResponseEntity.ok().body(orderRepository.list(entity, pageable).map(this::toResource));
    }

    @Override
    public ResponseEntity<OrderResource> getOrder(String orderId) {
        return orderRepository.find(orderId)
                .map(entity -> ResponseEntity.status(HttpStatus.OK).body(toResource(entity)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteOrder(String orderId) {
        return orderRepository.find(orderId)
                .map(entity -> {
                    orderRepository.delete(orderId);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private OrderResource toResource(OrderEntity entity) {
        return new OrderResource()
                .orderId(entity.getOrderId())
                .customerId(entity.getCustomerId())
                .itemName(entity.getItemName())
                .quantity(entity.getQuantity());
    }

    private OrderEntity toEntity(UpdateOrderCommand command, OrderEntity entity) {
        return new OrderEntity(entity.getOrderId(), entity.getCustomerId(), command.getItemName(), command.getQuantity());
    }

}
