package com.hack.demo.service;

import com.hack.demo.dto.LeaderboardResponse;
import com.hack.demo.entity.Team;
import com.hack.demo.repository.ScoreRepository;
import com.hack.demo.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {
    
    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private ScoreRepository scoreRepository;
    
    public List<LeaderboardResponse> getLeaderboard() {
        List<Team> teams = teamRepository.findAll();
        
        return teams.stream()
            .map(team -> {
                Long totalMarks = scoreRepository.getTotalMarksByTeamId(team.getId());
                String status = determineStatus(totalMarks);
                
                return new LeaderboardResponse(
                    team.getTeamName(),
                    totalMarks,
                    status
                );
            })
            .sorted((a, b) -> b.getTotalMarks().compareTo(a.getTotalMarks()))
            .collect(Collectors.toList());
    }
    
    private String determineStatus(Long totalMarks) {
        if (totalMarks >= 70) {
            return "Qualified";
        } else if (totalMarks >= 50) {
            return "Pending";
        } else {
            return "Rejected";
        }
    }
}
