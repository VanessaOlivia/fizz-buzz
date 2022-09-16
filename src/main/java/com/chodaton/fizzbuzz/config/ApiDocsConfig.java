package com.chodaton.fizzbuzz.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiDocsConfig {

    @Bean
    OpenAPI openApi(){
        return new OpenAPI().info(new Info().title("FIZZ BUZZ")
                .contact(new Contact().email("vanessachodaton@yahoo.fr").name("CHODATON Vanessa"))
                .version("1")
                .summary("fizz-buzz REST server")
                .description("REST API endpoints that:\n" +
                        "1. Accepts five parameters: three integers int1, int2 and limit, and two strings str1 and str2.\n" +
                        " And then Returns a list of strings with numbers from 1 to limit, where: all multiples of int1 are replaced \n" +
                        " by str1, all multiples of int2 are replaced by str2, all multiples of int1 and int2 are replaced by str1str2.\n " +
                        "2. Add a statistics endpoint allowing users to know what the most frequent request has been"));

    }


}