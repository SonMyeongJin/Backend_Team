package com.example.likelion12;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
public class Likelion12Application {

	public static void main(String[] args) {
		SpringApplication.run(Likelion12Application.class, args);
	}

}
