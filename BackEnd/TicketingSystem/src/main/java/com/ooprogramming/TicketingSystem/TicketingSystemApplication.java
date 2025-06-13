package com.ooprogramming.TicketingSystem;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class TicketingSystemApplication {

	@Autowired
	private TicketPool ticketPool;

	public static void main(String[] args) {
		SpringApplication.run(TicketingSystemApplication.class, args);
	}



}
