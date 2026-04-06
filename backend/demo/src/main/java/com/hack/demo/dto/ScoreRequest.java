package com.hack.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreRequest {
    
    @NotNull(message = "Team ID is required")
    private Long teamId;
    
    @NotNull(message = "Sprint Number is required")
    private Integer sprintNumber;
    
    @Min(value = 0, message = "Marks must be non-negative")
    private Integer marks = 0;
}
