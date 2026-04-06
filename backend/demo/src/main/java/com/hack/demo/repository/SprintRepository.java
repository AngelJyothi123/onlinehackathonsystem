package com.hack.demo.repository;

import com.hack.demo.entity.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Long> {
    
    Optional<Sprint> findBySprintNumber(Integer sprintNumber);
    
    boolean existsBySprintNumber(Integer sprintNumber);
}
