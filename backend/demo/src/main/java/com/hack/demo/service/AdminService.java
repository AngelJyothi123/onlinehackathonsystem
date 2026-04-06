package com.hack.demo.service;

import com.hack.demo.entity.Admin;
import com.hack.demo.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminService implements UserDetailsService {
    
    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Admin not found with username: " + username));
        
        return new org.springframework.security.core.userdetails.User(
            admin.getUsername(),
            admin.getPassword(),
            new ArrayList<>()
        );
    }
    
    public Admin createAdmin(String username, String password) {
        if (adminRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }
        
        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password));
        
        return adminRepository.save(admin);
    }
    
    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Admin not found with username: " + username));
    }
}
