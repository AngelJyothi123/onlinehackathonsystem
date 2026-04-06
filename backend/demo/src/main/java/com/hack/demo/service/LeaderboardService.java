package com.hack.demo.service;

import com.hack.demo.dto.LeaderboardResponse;
import com.hack.demo.dto.TeamMemberResponse;
import com.hack.demo.entity.Score;
import com.hack.demo.entity.Team;
import com.hack.demo.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {
    
    @Autowired
    private TeamRepository teamRepository;
    
    public List<LeaderboardResponse> getLeaderboard() {
        List<Team> teams = teamRepository.findAll();
        
        return teams.stream()
            .map(team -> {
                List<TeamMemberResponse> memberResponses = team.getMembers().stream()
                    .map(m -> new TeamMemberResponse(m.getId(), m.getMemberName(), team.getId()))
                    .collect(Collectors.toList());

                Integer sprint0 = 0;
                Integer sprint1 = 0;
                Integer sprint2 = 0;

                for (Score score : team.getScores()) {
                    if (score.getSprint() != null) {
                        if (score.getSprint().getSprintNumber() == 0) sprint0 = score.getMarks();
                        else if (score.getSprint().getSprintNumber() == 1) sprint1 = score.getMarks();
                        else if (score.getSprint().getSprintNumber() == 2) sprint2 = score.getMarks();
                    }
                }

                Long totalMarks = (long) (sprint0 + sprint1 + sprint2);
                String status = determineStatus(totalMarks);
                
                return new LeaderboardResponse(
                    team.getId(),
                    team.getTeamName(),
                    memberResponses,
                    sprint0,
                    sprint1,
                    sprint2,
                    totalMarks,
                    status
                );
            })
            .sorted((a, b) -> b.getTotalMarks().compareTo(a.getTotalMarks()))
            .collect(Collectors.toList());
    }
    
    private String determineStatus(Long totalMarks) {
        if (totalMarks >= 60) {
            return "Qualified";
        } else if (totalMarks > 0) {
            return "Rejected";
        } else {
            return "Pending";
        }
    }
}
