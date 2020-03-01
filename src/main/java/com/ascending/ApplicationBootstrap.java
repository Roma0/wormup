package com.ascending;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = {"com.ascending"})
@ServletComponentScan(basePackages = {"com.ascending.filter"})
public class ApplicationBootstrap {

    public static void main(String[] args){
        SpringApplication.run(ApplicationBootstrap.class, args);
    }

}
