package com.mkdika.spring5reactiveblog.web.handlers;

import com.mkdika.spring5reactiveblog.Spring5reactiveblogApplication;
import com.mkdika.spring5reactiveblog.model.Post;
import com.mkdika.spring5reactiveblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
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
        final Flux<Post> posts = Flux.from(repository.findAll());
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(posts, Post.class);
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
        Mono<Post> post = request.bodyToMono(Post.class);
        return post.flatMap(repository::save)
                .flatMap(p -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromObject(p)));
    }
}
