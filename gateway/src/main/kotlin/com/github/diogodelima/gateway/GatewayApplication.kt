package com.github.diogodelima.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean

@SpringBootApplication
class GatewayApplication {

    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator =
        builder.routes()
            .route("tasks-service") { route ->
                route
                    .path("/tasks/**")
                    .uri("http://tasks-service:8080")
            }
            .route("groups-service") { route ->
                route
                    .path("/groups/**")
                    .uri("http://groups-service:8080")
            }
            .build()

}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}