package com.hack.demo.controller;

import com.hack.demo.dto.ApiResponse;
import com.hack.demo.dto.ScoreRequest;
import com.hack.demo.entity.Score;
import com.hack.demo.service.ScoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scores")
@CrossOrigin(origins = "*")
public class ScoreController {
    
    @Autowired
    private ScoreService scoreService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<Score>> createOrUpdateScore(@Valid @RequestBody ScoreRequest scoreRequest) {
        try {
            Score response = scoreService.createOrUpdateScore(scoreRequest);
            return ResponseEntity.ok(ApiResponse.success("Score saved successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/team/{teamId}")
    public ResponseEntity<ApiResponse<List<Score>>> getScoresByTeamId(@PathVariable Long teamId) {
        try {
            List<Score> scores = scoreService.getScoresByTeamId(teamId);
            return ResponseEntity.ok(ApiResponse.success(scores));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/sprint/{sprintId}")
    public ResponseEntity<ApiResponse<List<Score>>> getScoresBySprintId(@PathVariable Long sprintId) {
        try {
            List<Score> scores = scoreService.getScoresBySprintId(sprintId);
            return ResponseEntity.ok(ApiResponse.success(scores));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(ApiResponse.error(e.getMessage()));
        }
    }
}
