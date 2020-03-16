package io.github.bhuwanupadhyay.springcloudcontractopenapisample.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Objects;

public class OrderEntity {

    @Id
    private final String orderId;
    private final String customerId;
    private final String itemName;
    private final Integer quantity;

    @PersistenceConstructor
    public OrderEntity(String orderId,
                       String customerId,
                       String itemName,
                       Integer quantity) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getOrderId() {
        return orderId;
    }
}
