package io.github.bhuwanupadhyay.shipmentsapiconsumer;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "shipments")
@Data
public class ShipmentConfiguration {

    /**
     * Orders API related configs
     */
    private OrdersApi ordersApi;

    @Data
    public static class OrdersApi {

        /**
         * base url for Orders API
         */
        private String baseUrl;

    }
}
