package com.example.eproject4.DTO.Response;

import com.example.eproject4.Entity.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackDTO {
    private Long id;
    private String subject;
    private String name;
    private String email;
    private String content;
    private Integer status;
    private Timestamp created_at;
    private Timestamp updated_at;
}
