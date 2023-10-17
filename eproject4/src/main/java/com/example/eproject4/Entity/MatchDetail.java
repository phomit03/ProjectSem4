package com.example.eproject4.Entity;

import com.example.eproject4.Utils.MapToDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.security.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "match_detail")
public class MatchDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @MapToDTO
    private Long id;

    @Column(name = "match_id")
    @MapToDTO
    private Long match_id;

    @Column(name = "shot")
    @MapToDTO
    private Integer shot;

    @Column(name = "shot_on_target")
    @MapToDTO
    private Integer shotOnTarget;

    @Column(name = "possesion")
    @MapToDTO
    private Integer possession;

    @Column(name = "foul")
    @MapToDTO
    private Integer foul;

    @Column(name = "passes")
    @MapToDTO
    private Integer passes;

    @Column(name = "pass_accuracy")
    @MapToDTO
    private Integer passAccuracy;

    @Column(name = "off_side")
    @MapToDTO
    private Integer offSide;

    @Column(name = "corner")
    @MapToDTO
    private Integer corner;

    @Column(name = "match_end", columnDefinition = "INT DEFAULT 0")
    @MapToDTO
    private Integer match_end;

    @Column(name = "status", columnDefinition = "INT DEFAULT 1")
    @MapToDTO
    private Integer status;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @MapToDTO
    private Timestamp createdAt;

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @MapToDTO
    private Timestamp updatedAt;

    @OneToOne
    @JoinColumn(name = "match_id", referencedColumnName = "id", insertable = false, updatable = false)
    @MapToDTO
    private Match match;
}