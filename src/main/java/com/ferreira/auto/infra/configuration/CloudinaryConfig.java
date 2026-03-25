package com.ferreira.auto.infra.configuration;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("${spring.cloudinary.name}")
    private String name;

    @Value("${spring.cloudinary.api.key}")
    private String apiKey;

    @Value("${spring.cloudinary.api.secret}")
    private String apiSecret;

    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", name);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);

        return new Cloudinary(config);
    }
}
