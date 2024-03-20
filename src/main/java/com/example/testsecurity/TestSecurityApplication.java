package com.example.testsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.example.testsecurity")
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.example.testsecurity"})
public class TestSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestSecurityApplication.class, args);
	}

}
