package com.hack.demo;

import com.hack.demo.entity.Admin;
import com.hack.demo.entity.Sprint;
import com.hack.demo.repository.AdminRepository;
import com.hack.demo.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private SprintRepository sprintRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Create default admin if not exists
        if (!adminRepository.existsByUsername("admin")) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            adminRepository.save(admin);
            System.out.println("Default admin created: username=admin, password=admin123");
        }
        
        // Create default sprints if not exist
        if (sprintRepository.count() == 0) {
            Sprint sprint0 = new Sprint();
            sprint0.setSprintNumber(0);
            
            Sprint sprint1 = new Sprint();
            sprint1.setSprintNumber(1);
            
            Sprint sprint2 = new Sprint();
            sprint2.setSprintNumber(2);
            
            sprintRepository.saveAll(Arrays.asList(sprint0, sprint1, sprint2));
            System.out.println("Default sprints created: 0, 1, 2");
        }
    }
}
