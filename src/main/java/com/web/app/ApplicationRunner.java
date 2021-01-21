package com.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The {@link SpringBootApplication} does really a lot of work,
 * basically, makes {@code Spring Boot} build {@link org.springframework.core.env.Environment},
 * {@link org.springframework.context.ApplicationContext} and many other things.
 * I learned about {@code Spring Boot} internals in <i>Eugeniy Borisov's</i> talk:
 * https://www.youtube.com/watch?v=8xa0RWMwAOE&t=2140s
 */
@SpringBootApplication
public class ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }
}