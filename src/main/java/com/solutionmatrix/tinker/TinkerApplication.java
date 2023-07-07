package com.solutionmatrix.tinker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = "com.solutionmatrix.tinker")
@ComponentScan(basePackages = { "com.solutionmatrix.tinker.*" })
@EntityScan("com.solutionmatrix.tinker.*")
@EnableWebMvc
public class TinkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TinkerApplication.class, args);
	}

}
