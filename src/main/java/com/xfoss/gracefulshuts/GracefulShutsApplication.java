package com.xfoss.gracefulshuts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GracefulShutsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GracefulShutsApplication.class, args);
	}

}
