package com.hack.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberRequest {
    
    @NotBlank(message = "Member name is required")
    @Size(max = 100, message = "Member name must not exceed 100 characters")
    private String memberName;
}
