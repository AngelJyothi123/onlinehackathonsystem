package com.hack.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardResponse {
    
    private Long id;
    private String teamName;
    private List<TeamMemberResponse> members;
    private Integer sprint0;
    private Integer sprint1;
    private Integer sprint2;
    private Long totalMarks;
    private String status;
}
