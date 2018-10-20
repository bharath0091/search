package com.search.router;

import com.search.handler.SearchHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ApplicationRouter {

    @Bean
    public RouterFunction<ServerResponse> routes(SearchHandler searchHandler) {
        return route(GET("/search").and(accept(APPLICATION_JSON)), searchHandler::search)
                .andRoute(GET("/healthCheck").and(accept(APPLICATION_JSON)), req -> ServerResponse.ok().build());
    }
}
