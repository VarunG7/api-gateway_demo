package com.microserrvices.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;

@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    //
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("book-service", r -> r
                        .path("/books", "/books/**")
                        .and()
                        .method(HttpMethod.GET)
                        .filters(f -> f
                                .addRequestHeader("X-Gateway", "api-gateway")
                                .rewritePath("/books(?<segment>/?.*)","/api/books${segment}")
                        )
                        .uri("lb://book-service"))
                .route("member-service", r -> r.path("/api/members/**")
                        .uri("lb://member-service"))
                .route("loan-service", r -> r.path("/api/loans/**")
                        .uri("lb://loan-service"))
                .route("notification-service", r -> r.path("/api/notifications/**")
                        .uri("lb://notification-service"))
                .build();
    }
}
