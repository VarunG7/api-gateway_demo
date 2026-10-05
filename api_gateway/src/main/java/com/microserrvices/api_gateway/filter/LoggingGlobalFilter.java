package com.microserrvices.api_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // BEFORE
        System.out.println(
                "BEFORE → "
                        + exchange.getRequest().getMethod()
                        + " "
                        + exchange.getRequest().getURI()
        );

        long startTime = System.currentTimeMillis();

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() -> {

                    // AFTER
                    long duration = System.currentTimeMillis() - startTime;

                    System.out.println(
                            "AFTER → "
                                    + exchange.getRequest().getMethod()
                                    + " "
                                    + exchange.getRequest().getURI()
                                    + " | Status: "
                                    + exchange.getResponse().getStatusCode()
                                    + " | Time: "
                                    + duration + " ms"
                    );
                }));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}