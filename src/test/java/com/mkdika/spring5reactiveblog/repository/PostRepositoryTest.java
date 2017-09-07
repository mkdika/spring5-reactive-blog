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
package com.mkdika.spring5reactiveblog.repository;

import com.mkdika.spring5reactiveblog.model.Comment;
import com.mkdika.spring5reactiveblog.model.Post;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

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
                "Lorem Ipsum",new Date(),null);
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
