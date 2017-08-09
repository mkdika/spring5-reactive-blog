package com.mkdika.spring5reactiveblog.repository;

import com.mkdika.spring5reactiveblog.model.Comment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CommentRepository extends ReactiveCrudRepository<Comment, Integer> {
}
