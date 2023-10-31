package com.example.eproject4.Entity.cart_order;

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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @MapToDTO
    private int id;
    @MapToDTO
    private int userId;
    @MapToDTO
    private float totalPrice;
    @MapToDTO
    private boolean status;
    @MapToDTO
    @Column(name = "created_at")
    private Timestamp createdAt;
    @MapToDTO
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
