package com.liter.demo;

import com.liter.demo.main.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class Literapp implements CommandLineRunner {

	@Autowired
	private Main main;

	public static void main(String[] args) {
		SpringApplication.run(Literapp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		main.showMenu();
	}
}


