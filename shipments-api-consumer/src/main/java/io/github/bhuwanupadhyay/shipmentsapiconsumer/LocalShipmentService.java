package io.github.bhuwanupadhyay.shipmentsapiconsumer;

import io.github.bhuwanupadhyay.shipmentsapiconsumer.ShipmentConfiguration.OrdersApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@EnableConfigurationProperties(ShipmentConfiguration.class)
@Slf4j
public class LocalShipmentService implements ShipmentService {

    private final RestTemplate webClient;
    private final OrdersApi ordersApi;

    public LocalShipmentService(ShipmentConfiguration shipment) {
        this.webClient = new RestTemplate();
        this.ordersApi = shipment.getOrdersApi();
    }

    @Override
    public boolean ship(String orderId, ShipmentAddress address) {
        OrderResource order = this.getOrder(orderId);
        log.info("Shipping order {} at address {}", order, address);
        return true;

    }

    private OrderResource getOrder(String orderId) {
        return this.webClient.getForObject(ordersApi.getBaseUrl() + "/orders/" + orderId, OrderResource.class);
    }

}
