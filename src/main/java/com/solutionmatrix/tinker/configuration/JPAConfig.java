package com.solutionmatrix.tinker.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@Configuration
@EnableJpaRepositories(basePackages = "com.solutionmatrix.tinker.repository")
public class JPAConfig {

}

