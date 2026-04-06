package com.hack.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaderboardResponse {
    
    private String teamName;
    private Long totalMarks;
    private String status;
}
