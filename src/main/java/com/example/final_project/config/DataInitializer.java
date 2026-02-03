package com.example.final_project.config;

import com.example.final_project.model.Role;
import com.example.final_project.model.User;
import com.example.final_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByEmail("admin@example.com")) {
            User admin = User.builder()
                    .name("Admin User")
                    .email("admin@example.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("Admin user created: admin@example.com / password");
        }

        if (!userRepository.existsByEmail("user@example.com")) {
            User user = User.builder()
                    .name("Regular User")
                    .email("user@example.com")
                    .password(passwordEncoder.encode("password"))
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
            System.out.println("Regular user created: user@example.com / password");
        }

        if (!userRepository.existsByEmail("admin2@example.nsbm.ac.lk")) {
            User admin2 = User.builder()
                    .name("admin2")
                    .email("admin2@example.nsbm.ac.lk")
                    .password(passwordEncoder.encode("Ad1234"))
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin2);
            System.out.println("✅ Test user created: email=admin2@example.nsbm.ac.lk, password=Ad1234");
        }

        if (!userRepository.existsByEmail("agent@proptech.com")) {
            User agent = User.builder()
                    .name("Agent Smith")
                    .email("agent@proptech.com")
                    .password(passwordEncoder.encode("AgentPassword123!"))
                    .role(Role.AGENT)
                    .build();
            userRepository.save(agent);
            System.out.println("✅ Agent user created: email=agent@proptech.com, password=AgentPassword123!");
        }
    }
}
