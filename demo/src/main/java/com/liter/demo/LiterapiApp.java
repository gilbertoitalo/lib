package com.liter.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.io.Console;

@SpringBootApplication
public class LiterapiAppl {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(LiterapiAppl.class, args);
		ConsoleUI consoleUI = context.getBean(ConsoleUI.class);
		ConsoleUI.start();
	}

}

@Bean
public RestTemplate restTemplate() {
	return new RestTemplate();
}


