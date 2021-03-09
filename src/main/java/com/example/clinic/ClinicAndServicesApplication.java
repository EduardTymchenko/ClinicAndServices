package com.example.clinic;

import com.example.clinic.repositories.ClinicRepository;
import org.apache.logging.log4j.spi.LoggerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClinicAndServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicAndServicesApplication.class, args);
	}

}
