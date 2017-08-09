package com.mkdika.spring5reactiveblog.web.handlers;

import com.mkdika.spring5reactiveblog.model.Post;
import com.mkdika.spring5reactiveblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class PostHandler {

    @Autowired
    private PostRepository repository;

    public Mono<ServerResponse> getPosts(ServerRequest request) {
        Flux<Post> products = this.repository.findAll();
        return ServerResponse.ok().contentType(APPLICATION_JSON).body(products, Post.class);
    }

    public Mono<ServerResponse> getPostById(ServerRequest request) {
        Integer postId = Integer.valueOf(request.pathVariable("id"));
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();
        Mono<Post> postMono = this.repository.findById(postId);
        return postMono
                .flatMap(post -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(post)))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> savePost(ServerRequest request) {
        Mono<Post> post = request.bodyToMono(Post.class);
        return ServerResponse.ok().build((BiFunction<ServerWebExchange, ServerResponse.Context, Mono<Void>>) this.repository.saveAll(post));
    }

    public Mono<ServerResponse> editPost(ServerRequest request) {
        return null;
    }

    public Mono<ServerResponse> deletePost(ServerRequest request) {
        Mono<Post> post = request.bodyToMono(Post.class);
        return ServerResponse.ok().build((BiFunction<ServerWebExchange, ServerResponse.Context, Mono<Void>>) this.repository.deleteAll(post));
    }
}
