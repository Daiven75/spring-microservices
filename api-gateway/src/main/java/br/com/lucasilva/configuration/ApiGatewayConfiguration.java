package br.com.lucasilva.configuration;

import br.com.lucasilva.model.Book;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;

@Configuration
public class ApiGatewayConfiguration {

    // example modify request or response
    Function<GatewayFilterSpec, UriSpec> filterModifyRoute =
            f -> f.addRequestHeader("test", "ok")
                    .modifyResponseBody(Book.class, Book.class, (ex, book) -> {
                book.setAuthor("Author successfully changed");
                return Mono.just(book);
            });

     // Option to configure routes using java code
//    @Bean
//    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(
//                        p -> p.path("/book-service/**")
////                                .filters(filterModifyRoute)
//                                .uri("lb://book-service"))
//                .route(
//                        p -> p.path("/cambio-service/**")
//                                .filters(
//                                        f -> f.addRequestParameter("amount", "5")
//                                                .addRequestParameter("from", "USD")
//                                                .addRequestParameter("to", "COP"))
//                                .uri("lb://cambio-service")
//                )
//                .route(p -> p.path("/**").uri("lb://nodejs-service"))
//                .build();
//    }
}
