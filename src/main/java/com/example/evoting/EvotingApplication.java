package com.example.evoting;

import com.example.evoting.model.User;
import com.example.evoting.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class EvotingApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvotingApplication.class, args);
	}


	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			Stream.of("Admin").forEach(name -> {
				User user = new User("Admin", "Admin", "admin@gmail.com", "admin", "admin", "000", true, false, false);
				userRepository.save(user);
			});
			userRepository.findAll().forEach(System.out::println);
		};

	}

}
