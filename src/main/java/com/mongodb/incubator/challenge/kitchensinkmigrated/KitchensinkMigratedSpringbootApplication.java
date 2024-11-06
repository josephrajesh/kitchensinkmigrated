package com.mongodb.incubator.challenge.kitchensinkmigrated;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class KitchensinkMigratedSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(KitchensinkMigratedSpringbootApplication.class, args);
	}

}
