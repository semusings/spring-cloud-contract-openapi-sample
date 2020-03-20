package io.github.bhuwanupadhyay.ordersapijava8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

}
