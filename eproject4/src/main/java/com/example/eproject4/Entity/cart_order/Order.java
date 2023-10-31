package com.example.eproject4.Entity.cart_order;

import com.example.eproject4.Entity.Team;
import com.example.eproject4.Entity.User;
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
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @MapToDTO
    private int id;

    @Column(name = "user_id")
    @MapToDTO
    private int userId;
    @MapToDTO
    private float totalPrice;

    private Boolean status;
    @MapToDTO
    @Column(name = "created_at")
    private Timestamp createdAt;
    @MapToDTO
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    @MapToDTO
    private User users;

    // Constructors, getters, and setters
}
