package com.example.Leave.Managment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LeaveManagmentApplication {


	public static void main(String[] args) {
		SpringApplication.run(LeaveManagmentApplication.class, args);
	}

}
