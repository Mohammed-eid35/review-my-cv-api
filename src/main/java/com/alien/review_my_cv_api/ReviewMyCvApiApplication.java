package com.alien.review_my_cv_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ReviewMyCvApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewMyCvApiApplication.class, args);
	}

}
