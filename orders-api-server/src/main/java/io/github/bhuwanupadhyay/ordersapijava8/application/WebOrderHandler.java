package io.github.bhuwanupadhyay.ordersapijava8.application;

import io.github.bhuwanupadhyay.ordersapijava8.domain.OrderEntity;
import io.github.bhuwanupadhyay.ordersapijava8.domain.OrderRepository;
import io.github.bhuwanupadhyay.ordersapijava8.openapi.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.data.domain.PageRequest.*;

@RestController
public class WebOrderHandler implements OrdersApi {

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
                                        UUID.randomUUID().toString(),
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
    public ResponseEntity<OrderPageList> listOrder(OrderResource filters, PageCommand command) {
        OrderEntity entity = new OrderEntity(
                filters.getOrderId(),
                filters.getCustomerId(),
                filters.getItemName(),
                filters.getQuantity()
        );

        Page<OrderResource> page = orderRepository.list(entity, toPageRequest(command)).map(this::toResource);
        return ResponseEntity.ok().body(new OrderPageList().content(page.getContent()).pageMetadata(toPageResource(page)));
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

    private <T> PageResource toPageResource(Page<T> page) {
        return new PageResource()
                .pageSize(page.getSize())
                .pageNumber(page.getNumber())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages());
    }

    private PageRequest toPageRequest(PageCommand command) {
        return Optional.ofNullable(command.getSort())
                .map(property -> {
                    Direction direction = Optional.ofNullable(command.getDirection())
                            .map(Direction::fromString).orElse(Direction.ASC);
                    return Sort.by(direction, property);
                })
                .map(sort -> PageRequest.of(command.getPageNumber(), command.getPageSize(), sort))
                .orElseGet(() -> PageRequest.of(command.getPageNumber(), command.getPageSize()));
    }

    private OrderEntity toEntity(UpdateOrderCommand command, OrderEntity entity) {
        return new OrderEntity(entity.getOrderId(), entity.getCustomerId(), command.getItemName(), command.getQuantity());
    }

}
