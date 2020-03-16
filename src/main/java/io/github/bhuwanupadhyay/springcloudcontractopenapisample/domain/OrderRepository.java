package io.github.bhuwanupadhyay.springcloudcontractopenapisample.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderRepository {

    Optional<OrderEntity> find(String orderId);

    Page<OrderEntity> list(OrderEntity filters, Pageable pageable);

    OrderEntity update(String orderId, OrderEntity changed);

    void delete(String orderId);

    OrderEntity save(OrderEntity entity);
}
