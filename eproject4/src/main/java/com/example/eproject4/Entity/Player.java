package com.example.eproject4.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "player")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String avatarImg;
    private Integer dateOfBirth;
    private String national;
    private String position;
    private Integer number;
    private Integer weight;
    private Integer height;
    private String achievement;
    private Integer teamId;
    private Integer status;
    private Date createdAt;
    private Date updatedAt;
}
