package io.github.bhuwanupadhyay.shipmentsapiconsumer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

public interface ShipmentService {

    boolean ship(String orderId, ShipmentAddress address);

    @Value
    class ShipmentAddress {
        private String addressLine1;
    }

    @Data
    class OrderResource {
        private String orderId;
        private String customerId;
        private String itemName;
        private Integer quantity;
    }


}
