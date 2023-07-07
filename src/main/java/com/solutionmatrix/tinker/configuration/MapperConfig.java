package com.solutionmatrix.tinker.configuration;

import com.solutionmatrix.tinker.mapper.ClientMapper;
import com.solutionmatrix.tinker.mapper.CustomClientMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ClientMapper clientMapper() {
        return new CustomClientMapper();
    }
}
