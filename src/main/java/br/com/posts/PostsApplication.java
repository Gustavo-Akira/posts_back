package br.com.posts;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableWebMvc
@EnableAutoConfiguration
@EnableWebSecurity
public class PostsApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(PostsApplication.class, args);
		//System.out.println(new BCryptPasswordEncoder().encode("kadeira"));
	}
}
