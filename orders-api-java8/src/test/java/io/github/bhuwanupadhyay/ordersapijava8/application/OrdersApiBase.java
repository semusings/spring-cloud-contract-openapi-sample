package io.github.bhuwanupadhyay.ordersapijava8.application;


import io.github.bhuwanupadhyay.ordersapijava8.domain.OrderEntity;
import io.github.bhuwanupadhyay.ordersapijava8.domain.OrderRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Objects;
import java.util.Optional;

public class OrdersApiBase {

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new WebOrderHandler(stubbedOrderRepository()));
    }

    private OrderRepository stubbedOrderRepository() {
        OrderEntity orderEntity = new OrderEntity("0a818933-087d-47f2-ad83-2f986ed087eb",
                "customerId1", "itemName1", 5);

        return new OrderRepository() {

            @Override
            public Optional<OrderEntity> find(String orderId) {
                return Optional.ofNullable(Objects.equals(orderId, orderEntity.getOrderId()) ? orderEntity : null);
            }

            @Override
            public Page<OrderEntity> list(OrderEntity filters, Pageable pageable) {
                return new PageImpl<>(Lists.list(orderEntity));
            }

            @Override
            public OrderEntity update(String orderId, OrderEntity changed) {
                return new OrderEntity(orderEntity.getOrderId(), orderEntity.getCustomerId(), changed.getItemName(), changed.getQuantity());
            }

            @Override
            public void delete(String orderId) {

            }

            @Override
            public OrderEntity save(OrderEntity entity) {
                return entity;
            }
        };
    }

    public void assertThatRejectionReasonIsNull(Object rejectionReason) {
        assert rejectionReason == null;
    }

}
