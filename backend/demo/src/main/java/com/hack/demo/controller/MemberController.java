package com.hack.demo.controller;

import com.hack.demo.dto.ApiResponse;
import com.hack.demo.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "*")
public class MemberController {
    
    @Autowired
    private TeamService teamService;
    
    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>> removeMember(@PathVariable Long memberId) {
        try {
            teamService.removeMember(memberId);
            return ResponseEntity.ok(ApiResponse.success("Member removed successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(e.getMessage()));
        }
    }
}
