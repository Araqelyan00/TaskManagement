package org.example.taskmanagement;

import org.example.taskmanagement.entity.User;
import org.example.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.example.taskmanagement.entity.UserType.MANAGER;

@EnableAsync
@SpringBootApplication
public class TaskManagementApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    public static void main(String[] args) {
        SpringApplication.run(TaskManagementApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Optional<User> byEmail = userRepository.findByEmail("admin@mail.com");
        if (byEmail.isEmpty()) {
            userRepository.save(User
                    .builder()
                    .name("admin")
                    .surname("admin")
                    .email("admin@mail.com")
                    .password(passwordEncoder().encode("admin"))
                    .role(MANAGER)
                    .build());
        }
    }
}
