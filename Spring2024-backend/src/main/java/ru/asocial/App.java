package ru.asocial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.asocial.controller", "ru.asocial.repo",
        "ru.asocial.filter", "ru.asocial.entity", "ru.asocial.service"})
@EnableJpaRepositories(basePackages = "ru.asocial.repo")
@EnableKafka
@EnableScheduling
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("begin");
        SpringApplication.run(App.class, args);
        System.out.println("end");
    }
}
