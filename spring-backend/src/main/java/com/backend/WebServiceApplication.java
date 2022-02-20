package com.backend;

import com.backend.Configuration.Config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class WebServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebServiceApplication.class, args);
  }

  @Bean
  public WebConfigurer corsConfigurer() {
      return new WebConfigurer();
  }

  private class WebConfigurer implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
                .allowedOrigins(Config.CORS_ALLOWED_ORIGINS)
                .allowCredentials(true);
    }
  }

}

