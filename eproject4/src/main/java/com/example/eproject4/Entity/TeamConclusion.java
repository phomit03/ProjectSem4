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
@Table(name = "team_conclusion")
public class TeamConclusion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @MapToDTO
    private Long id;
    @MapToDTO
    private Long team_id;
    @MapToDTO
    private Long win;
    @MapToDTO
    private Long lose;
    @MapToDTO
    private Long draw;
    @MapToDTO
    private Long point;
    @Column(name = "status", columnDefinition = "INT DEFAULT 1")
    @MapToDTO
    private Integer status;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @MapToDTO
    private Timestamp created_at;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @MapToDTO
    private Timestamp updated_at;

    @MapToDTO
    @OneToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Team team;
}
