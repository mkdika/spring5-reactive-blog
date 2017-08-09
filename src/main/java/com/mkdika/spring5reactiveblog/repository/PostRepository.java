package com.mkdika.spring5reactiveblog.repository;

import com.mkdika.spring5reactiveblog.model.Post;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PostRepository  extends ReactiveCrudRepository<Post, Integer> {

}
