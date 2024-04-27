package com.applydigital.hackernews.infrastructure.config.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AlgoliaClientConfig {

//    @Bean
//    public WebClient webClient(){
//        return WebClient.builder()
//                .baseUrl("https://algolia-api-url")
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .build();
//    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
