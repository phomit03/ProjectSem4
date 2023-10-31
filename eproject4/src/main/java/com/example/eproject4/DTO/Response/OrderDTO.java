package com.example.eproject4.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int id;
    private int userId;
    private float totalPrice;
    private boolean status;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}

