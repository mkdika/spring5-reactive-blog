package com.mkdika.spring5reactiveblog;

import com.mkdika.spring5reactiveblog.model.Post;
import com.mkdika.spring5reactiveblog.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
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
        repository.save(new Post(1, "About Spring Framework",
                "Mauris blandit aliquet elit, eget tincidunt nibh pulvinar a.",
                new Date(),null)).block();
        repository.save(new Post(2, "Whats new in Spring 5",
                "Mauris blandit aliquet elit, eget tincidunt nibh pulvinar a.",
                new Date(),null)).block();
        repository.save(new Post(3, "Spring Functional Web Framework",
                "Mauris blandit aliquet elit, eget tincidunt nibh pulvinar a.",
                new Date(), null)).block();
        repository.save(new Post(4, "Reactive Programming",
                "Mauris blandit aliquet elit, eget tincidunt nibh pulvinar a.",
                new Date(), null)).block();
    }
}
