package com.example.eproject4.Entity;

import com.example.eproject4.Utils.MapToDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "match_detail_event")
public class MatchDetailEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @MapToDTO
    private Long id;
    @MapToDTO
    private Long match_id;
    @MapToDTO
    private Long team_id;
    @MapToDTO
    private Long player_id;
    @MapToDTO
    private Integer type;
    @MapToDTO
    private String time;
    @MapToDTO
    private Timestamp createdAt;
    @MapToDTO
    private Timestamp updatedAt;
}
