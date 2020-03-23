package io.github.bhuwanupadhyay.ordersapijava8.application;


import io.github.bhuwanupadhyay.ordersapijava8.domain.EntityNotFoundException;
import io.github.bhuwanupadhyay.ordersapijava8.domain.OrderEntity;
import io.github.bhuwanupadhyay.ordersapijava8.domain.OrderRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

class OrdersApiBase {

    @BeforeEach
    void setup() {
        RestAssuredMockMvc.standaloneSetup(new WebOrderHandler(stubbedOrderRepository()));
    }

    private OrderRepository stubbedOrderRepository() {

        List<OrderEntity> entities = Lists.list(
                new OrderEntity(
                        "0a818933-087d-47f2-ad83-2f986ed087eb",
                        "Alpha",
                        "Hot Burger",
                        5
                )
        );

        return new OrderRepository() {
            @Override
            public Optional<OrderEntity> find(String orderId) {
                return entities
                        .stream()
                        .filter(orderEntity -> Objects.equals(orderId, orderEntity.getOrderId()))
                        .findFirst();
            }

            @Override
            public Page<OrderEntity> list(OrderEntity filters, Pageable pageable) {
                return new PageImpl<>(entities);
            }

            @Override
            public OrderEntity update(String orderId, OrderEntity changed) {
                OrderEntity orderEntity = find(orderId).orElseThrow(() -> new EntityNotFoundException(orderId));
                return new OrderEntity(orderEntity.getOrderId(), orderEntity.getCustomerId(), changed.getItemName(), changed.getQuantity());
            }

            @Override
            public void delete(String orderId) {
                find(orderId).orElseThrow(() -> new EntityNotFoundException(orderId));
            }

            @Override
            public OrderEntity save(OrderEntity entity) {
                return entity;
            }
        };
    }

    void assertThatRejectionReasonIsNull(Object rejectionReason) {
        assert rejectionReason == null;
    }

}
