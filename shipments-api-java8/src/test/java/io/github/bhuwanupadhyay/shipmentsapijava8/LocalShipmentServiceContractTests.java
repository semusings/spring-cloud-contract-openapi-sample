package io.github.bhuwanupadhyay.shipmentsapijava8;

import io.github.bhuwanupadhyay.shipmentsapijava8.ShipmentService.ShipmentAddress;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = {"io.github.bhuwanupadhyay:orders-api-java8:+:stubs:6569"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
class LocalShipmentServiceContractTests {

    @Autowired
    private ShipmentService service;

    @Test
    void shouldSuccessfullyShipAnOrder() {
        // given:
        String orderId = "orderId1";
        // when:
        boolean result = service.ship(orderId, new ShipmentAddress("Nepal"));
        // then:
        assertThat(result).isTrue();
    }

}