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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "area")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @MapToDTO
    private int id;

    @Column(name = "area_name")
    @MapToDTO
    private String area_name;

    @ManyToOne
    @JoinColumn(name = "stadium_id", referencedColumnName = "id")
    @MapToDTO
    private Stadium stadium;

    @Column(name = "status")
    @MapToDTO
    private Integer status;

    @Column(name = "created_at")
    @MapToDTO
    private Timestamp created_at;

    @Column(name = "updated_at")
    @MapToDTO
    private Timestamp updated_at;
}