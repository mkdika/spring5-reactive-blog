package com.mkdika.spring5reactiveblog.repository;

import com.mkdika.spring5reactiveblog.model.Post;
import com.mkdika.spring5reactiveblog.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@DataMongoTest
@RunWith(SpringRunner.class)
public class PostRepositoryTest {

    @Autowired
    PostRepository repository;

    @Test
    public void checkRecordCount() {
        assertEquals(new Long(4), repository.count().block());
    }


    @Test
    public void findByIdTest() {
        Mono<Post> post =  repository.findById(1);
        assertEquals("About Spring Framework",post.block().getTitle());
    }


    @Test
    public void existsById() {
        assertTrue(repository.existsById(2).block());
        assertFalse(repository.existsById(7).block());
    }

    @Test
    public void deleteById() {
        repository.deleteById(4);
        assertEquals(new Long(4),repository.count().block());
    }

}
