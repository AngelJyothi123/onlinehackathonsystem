package com.hack.demo.config;

import com.hack.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private AdminService adminService;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Try to load admin; if not found, create one
            adminService.findByUsername("admin");
        } catch (Exception e) {
            // Create admin with password "admin123"
            adminService.createAdmin("admin", "admin123");
            System.out.println("Created default admin with username 'admin' and password 'admin123'");
        }
    }
}
