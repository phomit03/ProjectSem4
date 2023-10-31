package com.example.eproject4.Entity;

import com.example.eproject4.Utils.MapToDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

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
    private String shot;

    @Column(name = "shot_on_target")
    @MapToDTO
    private String shotOnTarget;

    @Column(name = "possesion")
    @MapToDTO
    private String possession;

    @Column(name = "foul")
    @MapToDTO
    private String foul;

    @Column(name = "passes")
    @MapToDTO
    private String passes;

    @Column(name = "pass_accuracy")
    @MapToDTO
    private String passAccuracy;

    @Column(name = "off_side")
    @MapToDTO
    private String offSide;

    @Column(name = "corner")
    @MapToDTO
    private String corner;

    @Column(name = "yellow_card")
    @MapToDTO
    private String yellow_card;

    @Column(name = "red_card")
    @MapToDTO
    private String red_card;

    @Column(name = "match_end", columnDefinition = "INT DEFAULT 0")
    @MapToDTO
    private Integer match_end;

    @Column(name = "status", columnDefinition = "INT DEFAULT 1")
    @MapToDTO
    private Integer status;

    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @MapToDTO
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @MapToDTO
    private Timestamp updatedAt;

    @OneToOne
    @JoinColumn(name = "match_id", referencedColumnName = "id", insertable = false, updatable = false)
    @MapToDTO
    private Match match;
}
