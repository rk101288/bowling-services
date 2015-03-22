package com.rovergames.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.rovergames.bowling.persistence")
@EntityScan("com.rovergames.bowling.persistence")
@ComponentScan({"com.rovergames.bowling.controller", "com.rovergames.bowling.service"})
public class BowlingServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BowlingServicesApplication.class, args);
    }
}
