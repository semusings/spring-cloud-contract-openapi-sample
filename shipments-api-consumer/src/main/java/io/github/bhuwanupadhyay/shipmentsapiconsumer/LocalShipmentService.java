package io.github.bhuwanupadhyay.shipmentsapiconsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.bhuwanupadhyay.shipmentsapiconsumer.ShipmentConfiguration.OrdersApi;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.stream.Collectors;

@Service
@EnableConfigurationProperties(ShipmentConfiguration.class)
@Slf4j
public class LocalShipmentService implements ShipmentService {

    private final RestTemplate rest;
    private final OrdersApi ordersApi;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public LocalShipmentService(ShipmentConfiguration shipment,
                                RestTemplateBuilder restTemplateBuilder) {
        this.rest = restTemplateBuilder.errorHandler(new DownstreamApiRestTemplateErrorHandler()).build();
        this.ordersApi = shipment.getOrdersApi();
    }

    @Override
    public boolean ship(String orderId, ShipmentAddress address) {
        OrderResource order = this.getOrder(orderId);
        log.info("Shipping order {} at address {}", order, address);
        return true;

    }

    @SneakyThrows
    private OrderResource getOrder(String orderId) {

        URI uri = UriComponentsBuilder
                .fromUriString(ordersApi.getBaseUrl() + "/orders/{orderId}")
                .build(orderId);

        RequestEntity<Void> requestEntity = RequestEntity
                .get(uri)
                .build();

        ResponseEntity<String> exchanged = this.rest.exchange(requestEntity, String.class);
        return objectMapper.readValue(exchanged.getBody(), OrderResource.class);
    }

    @Slf4j
    public static class DownstreamApiRestTemplateErrorHandler extends DefaultResponseErrorHandler {

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
                    String httpBodyResponse = reader.lines().collect(Collectors.joining(""));
                    log.error("REST Error: {}", httpBodyResponse);
                    throw new DownstreamApiRestTemplateException("/orders/{orderId}", response.getStatusCode(), httpBodyResponse);
                }
            }

        }

        @AllArgsConstructor
        @Getter
        public static class DownstreamApiRestTemplateException extends RuntimeException {
            private final String path;
            private final HttpStatus httpStatus;
            private final String errorMessage;
        }
    }
}
