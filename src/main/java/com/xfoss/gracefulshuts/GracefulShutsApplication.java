package com.xfoss.gracefulshuts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(considerNestedRepositories = true)
public class GracefulShutsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GracefulShutsApplication.class, args);
	}

}
