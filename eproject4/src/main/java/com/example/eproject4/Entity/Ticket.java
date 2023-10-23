package com.example.eproject4.Entity;

import com.example.eproject4.Utils.MapToDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {
    @MapToDTO
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @MapToDTO
    @ManyToOne
    @JoinColumn(name = "area_id", nullable = false)
    private Area area;

    @MapToDTO
    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @MapToDTO
    private Integer quantity;

    @MapToDTO
    private Float price;

    @MapToDTO
    private Integer status;

    @MapToDTO
    @Column(name = "created_at")
    private Timestamp created_at;

    @MapToDTO
    @Column(name = "updated_at")
    private Timestamp updated_at;
}