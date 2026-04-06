package com.hack.demo.repository;

import com.hack.demo.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    
    Optional<Score> findByTeamIdAndSprintId(Long teamId, Long sprintId);
    
    @Query("SELECT s FROM Score s WHERE s.team.id = :teamId")
    List<Score> findByTeamId(@Param("teamId") Long teamId);
    
    @Query("SELECT s FROM Score s WHERE s.sprint.id = :sprintId")
    List<Score> findBySprintId(@Param("sprintId") Long sprintId);
    
    @Query("SELECT COALESCE(SUM(s.marks), 0) FROM Score s WHERE s.team.id = :teamId")
    Long getTotalMarksByTeamId(@Param("teamId") Long teamId);
}
