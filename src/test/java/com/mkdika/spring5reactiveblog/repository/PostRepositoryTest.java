package com.mkdika.spring5reactiveblog.repository;

import com.mkdika.spring5reactiveblog.model.Comment;
import com.mkdika.spring5reactiveblog.model.Post;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

@Ignore
@DataMongoTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PostRepositoryTest {

    @Autowired
    PostRepository repository;

    @Test
    public void t01CheckRecordCount() {
        assertEquals(new Long(4), repository.count().block());
    }


    @Test
    public void t02FindByIdTest() {
        Mono<Post> post =  repository.findById(1);
        assertEquals("About Spring Framework",post.block().getTitle());
    }


    @Test
    public void t03ExistsById() {
        assertTrue(repository.existsById(2).block());
        assertFalse(repository.existsById(7).block());
    }

    @Test
    public void t04DeleteById() {
        repository.deleteById(4).block();
        assertEquals(new Long(3),repository.count().block());
    }

    @Test
    public void t05DeleteAll() {
        repository.deleteAll().block();
        assertEquals(new Long(0),repository.count().block());
    }

    @Test
    public void t06Save() {
        Post post = new Post(1,"ZK Framework",
                "Lorem Ipsum",new Date());
        Comment comm = new Comment(1,"mkdika@gmail.com",new Date(),"Testing..");
        post.setComments(new ArrayList<>());
        post.getComments().add(comm);
        repository.save(post).block();
        Mono<Post> p =  repository.findById(1);
        assertEquals("ZK Framework",p.block().getTitle());
    }

    @Test
    public void t07ViewComment() {
        Mono<Post> post =  repository.findById(1);
        assertEquals("mkdika@gmail.com",post.block().getComments().get(0).getEmail());
    }
}
