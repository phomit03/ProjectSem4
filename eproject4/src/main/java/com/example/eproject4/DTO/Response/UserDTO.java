package com.example.eproject4.DTO.Response;

import com.example.eproject4.Entity.Stadium;
import com.example.eproject4.Entity.Team;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserDTO {
    private Long id;
    private String email;
    private String username;
    private String fullName;
    private LocalDate dateOfBirth;
    private String phone;
    private String address;
    private String status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
