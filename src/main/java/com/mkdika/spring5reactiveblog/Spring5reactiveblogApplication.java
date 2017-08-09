package com.mkdika.spring5reactiveblog;

import com.mkdika.spring5reactiveblog.model.Comment;
import com.mkdika.spring5reactiveblog.model.Post;
import com.mkdika.spring5reactiveblog.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class Spring5reactiveblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring5reactiveblogApplication.class, args);
	}

    /*
        To initialize dummy data into MongoDB
     */
	@Bean
    CommandLineRunner initData(PostRepository repository) {
        return (p) -> {
            populateData(repository);
        };
    }

    public static void populateData(PostRepository repository) {
        repository.deleteAll().block();
        Post p1 = new Post(1, "About Spring Framework",
                "Mauris blandit aliquet elit, eget tincidunt nibh pulvinar a.",
                new Date(),new ArrayList<>());
        p1.getComments().add(new Comment(1,"mkdika@gmail.com",new Date(),"Tes 123"));
        p1.getComments().add(new Comment(2,"jack@gmail.com",new Date(),"Tes xyz"));
        repository.save(p1).block();

        Post p2 = new Post(2, "Whats new in Spring 5",
                "Mauris blandit aliquet elit, eget tincidunt nibh pulvinar a.",
                new Date(),new ArrayList<>());
        p2.getComments().add(new Comment(3,"budi@gmail.com",new Date(),"Tes 234"));
        repository.save(p2).block();

        repository.save(new Post(3, "Spring Functional Web Framework",
                "Mauris blandit aliquet elit, eget tincidunt nibh pulvinar a.",
                new Date(), null)).block();
        repository.save(new Post(4, "Reactive Programming",
                "Mauris blandit aliquet elit, eget tincidunt nibh pulvinar a.",
                new Date(), null)).block();

    }
}
