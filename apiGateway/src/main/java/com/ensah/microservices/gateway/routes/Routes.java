package com.ensah.microservices.gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.FilterFunctions.setPath;

@Configuration
public class Routes {

    @Bean
    public RouterFunction<ServerResponse> productServiceRoutes() {
        return GatewayRouterFunctions.route("product-service")
                .route(RequestPredicates.path("/api/products"), HandlerFunctions.http("http://localhost:8080"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> productServiceSwaggerRoutes() {
        return GatewayRouterFunctions.route("product-service-swagger")
                .route(RequestPredicates.path("/aggregate/product-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8080"))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceRoutes() {
        return GatewayRouterFunctions.route("order-service")
                .route(RequestPredicates.path("/api/order"), HandlerFunctions.http("http://localhost:8081"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> orderServiceSwaggerRoutes() {
        return GatewayRouterFunctions.route("order-service-swagger")
                .route(RequestPredicates.path("/aggregate/order-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8081"))
                .filter(setPath("/api-docs"))
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoutes() {
        return GatewayRouterFunctions.route("inventory-service")
                .route(RequestPredicates.path("/api/inventory"), HandlerFunctions.http("http://localhost:8082"))
                .build();
    }
    @Bean
    public RouterFunction<ServerResponse> inventoryServiceSwaggerRoutes() {
        return GatewayRouterFunctions.route("inventory-service-swagger")
                .route(RequestPredicates.path("/aggregate/inventory-service/v3/api-docs"), HandlerFunctions.http("http://localhost:8082"))
                .filter(setPath("/api-docs"))
                .build();
    }
}
