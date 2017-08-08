package com.mkdika.spring5reactiveblog.web.handlers;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface PostHandler {

    public Mono<ServerResponse> getPosts(ServerRequest request);
    public Mono<ServerResponse> getPostById(ServerRequest request);
    public Mono<ServerResponse> savePost(ServerRequest request);
    public Mono<ServerResponse> editPost(ServerRequest request);
    public Mono<ServerResponse> deletePost(ServerRequest request);
}
