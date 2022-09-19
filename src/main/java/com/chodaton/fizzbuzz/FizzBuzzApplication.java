package com.chodaton.fizzbuzz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class FizzBuzzApplication {
	public static void main(String[] args) {
		SpringApplication.run(FizzBuzzApplication.class, args);
	}

}
