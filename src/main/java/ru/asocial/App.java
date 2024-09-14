package ru.asocial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.asocial.controller", "ru.asocial.repo", "ru.asocial.filter"})
public class App 
{
    public static void main( String[] args )
    {
        System.out.println("begin");
        SpringApplication.run(App.class, args);
        System.out.println("end");
    }
}
