package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootPracticeApplication.class, args);
		System.out.println("Hello");
	}

//    com.example.demo
//├── controller       // Handles HTTP requests and maps them to service methods
//├── service          // Contains business logic
//├── repository       // Interfaces for data access (DAO layer)
//├── model            // POJOs representing entities
//├── dto              // Data Transfer Objects (optional, for request/response shaping)
//├── exception        // Custom exceptions and global exception handling
//├── config           // Configuration classes (e.g., CORS, Swagger, etc.)
//└── util             // Utility/helper classes


}
