package com.hack.demo.controller;

import com.hack.demo.dto.ApiResponse;
import com.hack.demo.dto.TeamMemberRequest;
import com.hack.demo.dto.TeamMemberResponse;
import com.hack.demo.dto.TeamRequest;
import com.hack.demo.dto.TeamResponse;
import com.hack.demo.service.TeamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin(origins = "*")
public class TeamController {
    
    @Autowired
    private TeamService teamService;
    
    @PostMapping
    public ResponseEntity<ApiResponse<TeamResponse>> createTeam(@Valid @RequestBody TeamRequest teamRequest) {
        try {
            TeamResponse response = teamService.createTeam(teamRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Team created successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<TeamResponse>>> getAllTeams() {
        List<TeamResponse> teams = teamService.getAllTeams();
        return ResponseEntity.ok(ApiResponse.success(teams));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TeamResponse>> getTeamById(@PathVariable Long id) {
        try {
            TeamResponse response = teamService.getTeamById(id);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTeam(@PathVariable Long id) {
        try {
            teamService.deleteTeam(id);
            return ResponseEntity.ok(ApiResponse.success("Team deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<TeamResponse>>> searchTeams(@RequestParam String name) {
        List<TeamResponse> teams = teamService.searchTeamsByName(name);
        return ResponseEntity.ok(ApiResponse.success(teams));
    }
    
    @PostMapping("/{teamId}/members")
    public ResponseEntity<ApiResponse<TeamMemberResponse>> addMember(
            @PathVariable Long teamId, 
            @Valid @RequestBody TeamMemberRequest memberRequest) {
        try {
            TeamMemberResponse response = teamService.addMemberToTeam(teamId, memberRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Member added successfully", response));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(e.getMessage()));
        }
    }
}
