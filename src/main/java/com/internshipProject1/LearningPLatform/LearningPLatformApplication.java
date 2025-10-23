package com.internshipProject1.LearningPLatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LearningPLatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningPLatformApplication.class, args);
	}

}
