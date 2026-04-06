package com.hack.demo.service;

import com.hack.demo.dto.ScoreRequest;
import com.hack.demo.entity.Score;
import com.hack.demo.entity.Sprint;
import com.hack.demo.entity.Team;
import com.hack.demo.exception.ResourceNotFoundException;
import com.hack.demo.repository.ScoreRepository;
import com.hack.demo.repository.SprintRepository;
import com.hack.demo.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScoreService {
    
    @Autowired
    private ScoreRepository scoreRepository;
    
    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private SprintRepository sprintRepository;
    
    public Score createOrUpdateScore(ScoreRequest scoreRequest) {
        Team team = teamRepository.findById(scoreRequest.getTeamId())
            .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + scoreRequest.getTeamId()));
        
        Sprint sprint = sprintRepository.findById(scoreRequest.getSprintId())
            .orElseThrow(() -> new ResourceNotFoundException("Sprint not found with id: " + scoreRequest.getSprintId()));
        
        Score existingScore = scoreRepository.findByTeamIdAndSprintId(
            scoreRequest.getTeamId(), scoreRequest.getSprintId()).orElse(null);
        
        if (existingScore != null) {
            existingScore.setMarks(scoreRequest.getMarks());
            return scoreRepository.save(existingScore);
        } else {
            Score newScore = new Score();
            newScore.setTeam(team);
            newScore.setSprint(sprint);
            newScore.setMarks(scoreRequest.getMarks());
            return scoreRepository.save(newScore);
        }
    }
    
    public List<Score> getScoresByTeamId(Long teamId) {
        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + teamId));
        return scoreRepository.findByTeamId(teamId);
    }
    
    public List<Score> getScoresBySprintId(Long sprintId) {
        Sprint sprint = sprintRepository.findById(sprintId)
            .orElseThrow(() -> new ResourceNotFoundException("Sprint not found with id: " + sprintId));
        return scoreRepository.findBySprintId(sprintId);
    }
    
    public Long getTotalMarksByTeamId(Long teamId) {
        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + teamId));
        return scoreRepository.getTotalMarksByTeamId(teamId);
    }
}
