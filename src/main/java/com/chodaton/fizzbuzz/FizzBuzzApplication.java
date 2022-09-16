package com.chodaton.fizzbuzz;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SecurityScheme(name = "fizzbuzzapi_acces", scheme = "basic",
		type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@SpringBootApplication
public class FizzBuzzApplication {
	public static void main(String[] args) {
		SpringApplication.run(FizzBuzzApplication.class, args);
	}


}
