package com.example.clinic.configuration;

import com.example.clinic.domain.Clinic;
import com.example.clinic.domain.Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

@Configuration
public class AppConfig {

    @Bean
    public RowMapper<Clinic> clinicRowMapper(){
        return new BeanPropertyRowMapper<>(Clinic.class);
    }

    @Bean
    public RowMapper<Service> serviceRowMapper(){
        return new BeanPropertyRowMapper<>(Service.class);
    }

}
