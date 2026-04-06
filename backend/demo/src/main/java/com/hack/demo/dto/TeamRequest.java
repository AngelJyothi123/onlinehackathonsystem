package com.hack.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamRequest {
    
    @NotBlank(message = "Team name is required")
    @Size(max = 150, message = "Team name must not exceed 150 characters")
    private String teamName;
}
