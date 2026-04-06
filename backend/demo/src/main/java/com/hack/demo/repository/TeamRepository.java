package com.hack.demo.repository;

import com.hack.demo.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    
    Optional<Team> findByTeamName(String teamName);
    
    boolean existsByTeamName(String teamName);
    
    @Query("SELECT t FROM Team t WHERE t.teamName LIKE %:name%")
    List<Team> findByTeamNameContaining(@Param("name") String name);
}
