package com.etore.userservice;

import org.axonframework.springboot.autoconfig.AxonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.trutran.estore.core.config.XStreamConfig;

@SpringBootApplication(exclude = AxonAutoConfiguration.class)
@Import(XStreamConfig.class)
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

}
