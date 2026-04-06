package com.hack.demo.service;

import com.hack.demo.dto.TeamMemberRequest;
import com.hack.demo.dto.TeamMemberResponse;
import com.hack.demo.dto.TeamRequest;
import com.hack.demo.dto.TeamResponse;
import com.hack.demo.entity.Team;
import com.hack.demo.entity.TeamMember;
import com.hack.demo.exception.ResourceNotFoundException;
import com.hack.demo.exception.BusinessException;
import com.hack.demo.repository.TeamMemberRepository;
import com.hack.demo.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamService {
    
    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private TeamMemberRepository teamMemberRepository;
    
    public TeamResponse createTeam(TeamRequest teamRequest) {
        if (teamRepository.existsByTeamName(teamRequest.getTeamName())) {
            throw new BusinessException("Team with name '" + teamRequest.getTeamName() + "' already exists");
        }
        
        Team team = new Team();
        team.setTeamName(teamRequest.getTeamName());
        
        Team savedTeam = teamRepository.save(team);
        return convertToTeamResponse(savedTeam);
    }
    
    public List<TeamResponse> getAllTeams() {
        return teamRepository.findAll().stream()
            .map(this::convertToTeamResponse)
            .collect(Collectors.toList());
    }
    
    public TeamResponse getTeamById(Long id) {
        Team team = teamRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));
        return convertToTeamResponse(team);
    }
    
    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));
        teamRepository.delete(team);
    }
    
    public List<TeamResponse> searchTeamsByName(String name) {
        return teamRepository.findByTeamNameContaining(name).stream()
            .map(this::convertToTeamResponse)
            .collect(Collectors.toList());
    }
    
    public TeamMemberResponse addMemberToTeam(Long teamId, TeamMemberRequest memberRequest) {
        Team team = teamRepository.findById(teamId)
            .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + teamId));
        
        long memberCount = teamMemberRepository.countByTeamId(teamId);
        if (memberCount >= 4) {
            throw new BusinessException("Team cannot have more than 4 members");
        }
        
        TeamMember member = new TeamMember();
        member.setMemberName(memberRequest.getMemberName());
        member.setTeam(team);
        
        TeamMember savedMember = teamMemberRepository.save(member);
        return convertToTeamMemberResponse(savedMember);
    }
    
    public void removeMember(Long memberId) {
        TeamMember member = teamMemberRepository.findById(memberId)
            .orElseThrow(() -> new ResourceNotFoundException("Team member not found with id: " + memberId));
        teamMemberRepository.delete(member);
    }
    
    private TeamResponse convertToTeamResponse(Team team) {
        TeamResponse response = new TeamResponse();
        response.setId(team.getId());
        response.setTeamName(team.getTeamName());
        response.setCreatedAt(team.getCreatedAt());
        response.setMembers(team.getMembers().stream()
            .map(this::convertToTeamMemberResponse)
            .collect(Collectors.toList()));
        return response;
    }
    
    private TeamMemberResponse convertToTeamMemberResponse(TeamMember member) {
        TeamMemberResponse response = new TeamMemberResponse();
        response.setId(member.getId());
        response.setMemberName(member.getMemberName());
        response.setTeamId(member.getTeam().getId());
        return response;
    }
}
