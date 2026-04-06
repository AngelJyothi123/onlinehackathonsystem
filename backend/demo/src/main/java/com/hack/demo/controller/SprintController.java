package com.hack.demo.controller;

import com.hack.demo.dto.ApiResponse;
import com.hack.demo.dto.SprintResponse;
import com.hack.demo.service.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sprints")
@CrossOrigin(origins = "*")
public class SprintController {
    
    @Autowired
    private SprintService sprintService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<SprintResponse>>> getAllSprints() {
        List<SprintResponse> sprints = sprintService.getAllSprints();
        return ResponseEntity.ok(ApiResponse.success(sprints));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SprintResponse>> getSprintById(@PathVariable Long id) {
        try {
            SprintResponse response = sprintService.getSprintById(id);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(e.getMessage()));
        }
    }
}
