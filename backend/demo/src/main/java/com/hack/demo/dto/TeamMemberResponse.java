package com.hack.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberResponse {
    
    private Long id;
    private String memberName;
    private Long teamId;
}
