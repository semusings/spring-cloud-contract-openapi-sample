package io.github.bhuwanupadhyay.shipmentsapijava8;

import io.github.bhuwanupadhyay.shipmentsapijava8.ShipmentConfiguration.OrdersApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@EnableConfigurationProperties(ShipmentConfiguration.class)
@Slf4j
public class LocalShipmentService implements ShipmentService {

    private final WebClient webClient;
    private final OrdersApi ordersApi;

    public LocalShipmentService(WebClient webClient,
                                ShipmentConfiguration shipment) {
        this.webClient = webClient;
        this.ordersApi = shipment.getOrdersApi();
    }

    @Override
    public boolean ship(String orderId, ShipmentAddress address) {
        OrderResource order = this.getOrder(orderId);
        log.info("Shipping order {} at address {}", order, address);
        return true;

    }

    private OrderResource getOrder(String orderId) {
        return this.webClient.get()
                .uri(builder -> builder
                        .path(ordersApi.getBaseUrl() + "/orders/" + orderId)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(OrderResource.class)
                .block();
    }

}
