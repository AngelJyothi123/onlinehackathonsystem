package com.hack.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "scores", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"team_id", "sprint_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Team team;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Sprint sprint;
    
    @Column(name = "marks", nullable = false)
    private Integer marks = 0;
}
