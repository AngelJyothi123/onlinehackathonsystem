package com.hack.demo.config;

import com.hack.demo.entity.Sprint;
import com.hack.demo.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private SprintRepository sprintRepository;

    @Override
    public void run(String... args) throws Exception {
        // Ensure Sprint 0, 1, 2 exist
        List<Integer> requiredSprints = Arrays.asList(0, 1, 2);
        for (Integer sprintNum : requiredSprints) {
            if (!sprintRepository.findBySprintNumber(sprintNum).isPresent()) {
                Sprint sprint = new Sprint();
                sprint.setSprintNumber(sprintNum);
                sprintRepository.save(sprint);
                System.out.println("Initialized Sprint " + sprintNum);
            }
        }
    }
}
