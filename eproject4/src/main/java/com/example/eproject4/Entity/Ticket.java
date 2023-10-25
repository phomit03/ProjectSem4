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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ticket")
public class Ticket {
    @MapToDTO
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @MapToDTO
    @JoinColumn(name = "area_id", nullable = false)
    private Long area_id;

    @MapToDTO
    private Long match_id;

    @MapToDTO
    private Integer quantity;

    @MapToDTO
    private Float price;

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
    @ManyToOne
    @JoinColumn(name = "match_id", insertable = false, updatable = false)
    private Match match;

    @MapToDTO
    @ManyToOne
    @JoinColumn(name = "area_id", nullable = false, insertable = false, updatable = false)
    private Area area;
}