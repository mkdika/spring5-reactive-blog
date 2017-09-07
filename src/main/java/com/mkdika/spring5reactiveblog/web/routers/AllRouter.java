/*
 * The MIT License
 *
 * Copyright 2017 Maikel Chandika <mkdika@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mkdika.spring5reactiveblog.web.routers;

import com.mkdika.spring5reactiveblog.web.handlers.PostHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

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
