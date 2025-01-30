package com.example.employeemanagementsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeemanagementsysApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeemanagementsysApplication.class, args);
	}

}
/*
*
spring.datasource.url=jdbc:postgresql://localhost:1234/learning_management_system
spring.datasource.username=Wassem
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database=postgresql

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
*/