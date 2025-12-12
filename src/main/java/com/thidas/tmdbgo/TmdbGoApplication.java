package com.thidas.tmdbgo;

import com.thidas.tmdbgo.client.TmdbProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableConfigurationProperties(TmdbProperties.class)
@EnableCaching
public class TmdbGoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmdbGoApplication.class, args);
    }

}
