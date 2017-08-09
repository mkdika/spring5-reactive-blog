package com.mkdika.spring5reactiveblog;

import com.mkdika.spring5reactiveblog.model.Post;
import com.mkdika.spring5reactiveblog.repository.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import reactor.ipc.netty.http.server.HttpServer;

import java.util.Date;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class Spring5reactiveblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring5reactiveblogApplication.class, args);
	}

    @Bean
    CommandLineRunner initData(PostRepository repository) {
        return (p) -> {
            repository.deleteAll().block();
            repository.save(new Post(1, "Post ABC", "ABC is a", new Date())).block();
            repository.save(new Post(2, "Post XYZ", "XYZ is a", new Date())).block();
        };
    }

    @Bean
    public HttpServer server(RouterFunction<?> router){
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(router);
        ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
        HttpServer server = HttpServer.create("localhost", 8123);
        server.start(adapter);
        return server;
    }
}
