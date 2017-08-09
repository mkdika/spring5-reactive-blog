package com.mkdika.spring5reactiveblog.web.routers;

import com.mkdika.spring5reactiveblog.web.handlers.PostHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Component
public class AllRouter {

    @Bean
    public RouterFunction<?> routers(PostHandler handler){
        return RouterFunctions.route(RequestPredicates.GET("/post"), handler::getPosts).
                and(RouterFunctions.route(RequestPredicates.GET("/post/{id}"), handler::getPostById));

    }

}
