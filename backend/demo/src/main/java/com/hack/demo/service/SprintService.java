package com.hack.demo.service;

import com.hack.demo.dto.SprintResponse;
import com.hack.demo.entity.Sprint;
import com.hack.demo.exception.ResourceNotFoundException;
import com.hack.demo.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SprintService {
    
    @Autowired
    private SprintRepository sprintRepository;
    
    public List<SprintResponse> getAllSprints() {
        return sprintRepository.findAll().stream()
            .map(this::convertToSprintResponse)
            .collect(Collectors.toList());
    }
    
    public SprintResponse getSprintById(Long id) {
        Sprint sprint = sprintRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sprint not found with id: " + id));
        return convertToSprintResponse(sprint);
    }
    
    public SprintResponse getSprintByNumber(Integer sprintNumber) {
        Sprint sprint = sprintRepository.findBySprintNumber(sprintNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Sprint not found with number: " + sprintNumber));
        return convertToSprintResponse(sprint);
    }
    
    public SprintResponse createSprint(Integer sprintNumber) {
        if (sprintRepository.existsBySprintNumber(sprintNumber)) {
            throw new RuntimeException("Sprint with number " + sprintNumber + " already exists");
        }
        
        Sprint sprint = new Sprint();
        sprint.setSprintNumber(sprintNumber);
        
        Sprint savedSprint = sprintRepository.save(sprint);
        return convertToSprintResponse(savedSprint);
    }
    
    private SprintResponse convertToSprintResponse(Sprint sprint) {
        SprintResponse response = new SprintResponse();
        response.setId(sprint.getId());
        response.setSprintNumber(sprint.getSprintNumber());
        return response;
    }
}
