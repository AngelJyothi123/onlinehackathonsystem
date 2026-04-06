package com.hack.demo.repository;

import com.hack.demo.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    
    @Query("SELECT COUNT(tm) FROM TeamMember tm WHERE tm.team.id = :teamId")
    long countByTeamId(@Param("teamId") Long teamId);
    
    List<TeamMember> findByTeamId(Long teamId);
}
