package br.com.taian.deal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DealApplication {

    public static void main(String[] args) {
        SpringApplication.run(DealApplication.class, args);
    }

}
