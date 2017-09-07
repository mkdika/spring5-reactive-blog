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
package com.mkdika.spring5reactiveblog.web.handlers;

import com.mkdika.spring5reactiveblog.Spring5reactiveblogApplication;
import com.mkdika.spring5reactiveblog.model.Post;
import com.mkdika.spring5reactiveblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class PostHandler {

    @Autowired
    private PostRepository repository;

    public Mono<ServerResponse> initData(ServerRequest request) {
        Spring5reactiveblogApplication.populateData(repository);
        return ServerResponse.ok()
                .build(Mono.empty());
    }

    public Mono<ServerResponse> getPosts(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(repository.findAll(), Post.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getPostById(ServerRequest request) {
        Integer postId = Integer.valueOf(request.pathVariable("id"));
        return repository.findById(postId)
                .flatMap(post -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(post)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteById(ServerRequest request) {
        Integer postId = Integer.valueOf(request.pathVariable("id"));
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .build(repository.deleteById(postId))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteAllPost(ServerRequest request) {
        return ServerResponse.ok()
                .build(this.repository.deleteAll());
    }

    public Mono<ServerResponse> saveUpdatePost(ServerRequest request) {
        return request.bodyToMono(Post.class).flatMap(repository::save)
                .flatMap(p -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(p)));
    }
}
