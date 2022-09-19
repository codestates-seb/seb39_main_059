package com.twentyfour_seven.catvillage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CatvillageApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatvillageApplication.class, args);
	}

}
