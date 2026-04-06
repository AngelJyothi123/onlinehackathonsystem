package com.hack.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamResponse {
    
    private Long id;
    private String teamName;
    private LocalDateTime createdAt;
    private List<TeamMemberResponse> members;
}
