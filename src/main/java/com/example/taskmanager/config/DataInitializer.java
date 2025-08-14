package com.example.taskmanager.config;

import com.example.taskmanager.model.Role;
import com.example.taskmanager.model.User;
import com.example.taskmanager.repository.RoleRepository;
import com.example.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (roleRepo.findByName("USER").isEmpty())
            roleRepo.save(new Role("USER"));

        if (roleRepo.findByName("ADMIN").isEmpty())
            roleRepo.save(new Role("ADMIN"));

        if (userRepo.findByUsername("simohamed").isEmpty()) {
            User user = new User("simohamed", passwordEncoder.encode("qwerty"));
            Role role = roleRepo.findByName("ADMIN").orElseThrow();
            user.setRoles(Set.of(role));
            userRepo.save(user);
        }
    }
}