package de.fraunhofer.iem.secucheck.todolist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import de.fraunhofer.iem.secucheck.todolist.service.StorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	}

}
