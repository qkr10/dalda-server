package com.dalda.dalda_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DaldaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DaldaServerApplication.class, args);
	}

}
