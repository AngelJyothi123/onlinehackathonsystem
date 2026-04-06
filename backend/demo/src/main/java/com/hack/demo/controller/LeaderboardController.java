package com.hack.demo.controller;

import com.hack.demo.dto.ApiResponse;
import com.hack.demo.dto.LeaderboardResponse;
import com.hack.demo.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaderboard")
@CrossOrigin(origins = "*")
public class LeaderboardController {
    
    @Autowired
    private LeaderboardService leaderboardService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<LeaderboardResponse>>> getLeaderboard() {
        List<LeaderboardResponse> leaderboard = leaderboardService.getLeaderboard();
        return ResponseEntity.ok(ApiResponse.success(leaderboard));
    }
}
