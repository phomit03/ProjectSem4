package com.example.eproject4.Entity;

import com.example.eproject4.Utils.MapToDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "matches")
public class Match{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @MapToDTO
    private Long id;

    @Column(name = "match_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @MapToDTO
    private LocalDateTime match_time;

    @MapToDTO
    @OneToOne
    @JoinColumn(name = "home_team_id", referencedColumnName = "id")
    private Team home_team_id;

    @MapToDTO
    @OneToOne
    @JoinColumn(name = "away_team_id", referencedColumnName = "id")
    private Team away_team_id;

    @MapToDTO
    @ManyToOne
    @JoinColumn(name = "stadium_id", referencedColumnName = "id")
    private Stadium stadium_id;

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

    @ManyToOne
    @JoinColumn(name = "home_team_id", referencedColumnName = "id", insertable = false, updatable = false)
    @MapToDTO
    private Team home_team;

    @ManyToOne
    @JoinColumn(name = "away_team_id", referencedColumnName = "id", insertable = false, updatable = false)
    @MapToDTO
    private Team away_team;

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public String getFormattedMatchTime() {
        return match_time.format(dateTimeFormatter);
    }
}