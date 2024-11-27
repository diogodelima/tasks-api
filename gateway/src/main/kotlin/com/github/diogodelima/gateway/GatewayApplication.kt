package com.github.diogodelima.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.server.ServerWebExchange

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
                    .filters { filter -> filter.filter(userIdFilter(webClient()))}
                    .uri("http://groups-service:8080")
            }
            .build()

    @Bean
    fun userIdFilter(webClient: WebClient): GatewayFilter {
        return GatewayFilter { exchange: ServerWebExchange, chain: GatewayFilterChain ->

            val userIdMono = webClient.get()
                .uri("/userinfo")
                .header("Authorization", exchange.request.headers["Authorization"]?.first())
                .retrieve()
                .bodyToMono(Map::class.java)
                .mapNotNull { it["user_id"] as? Int }

            userIdMono.flatMap { userId ->
                val modifiedExchange = exchange.mutate()
                    .request { req -> req.header("userId", userId.toString()) }
                    .build()

                chain.filter(modifiedExchange)
            }
        }
    }

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
            .baseUrl("http://authorization-server:8080")
            .build()
    }

}

fun main(args: Array<String>) {
    runApplication<GatewayApplication>(*args)
}