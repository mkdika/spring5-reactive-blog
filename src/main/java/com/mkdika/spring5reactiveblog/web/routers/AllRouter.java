package com.mkdika.spring5reactiveblog.web.routers;

import com.mkdika.spring5reactiveblog.web.handlers.PostHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Component
public class AllRouter {

    @Bean
    public RouterFunction<ServerResponse> routers(PostHandler handler){
        return RouterFunctions.route(GET("/post"), handler::getPosts).
                and(RouterFunctions.route(GET("/post/{id}"), handler::getPostById)).
                and(RouterFunctions.route(POST("/post/init"), handler::initData)).
                and(RouterFunctions.route(POST("/post") , handler::saveUpdatePost)).
                and(RouterFunctions.route(PUT("/post") , handler::saveUpdatePost)).
                and(RouterFunctions.route(DELETE("/post/{id}"), handler::deleteById)).
                and(RouterFunctions.route(DELETE("/post"), handler::deleteAllPost));
    }
}
