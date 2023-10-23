package com.example.eproject4.Entity.cart_order;

import com.example.eproject4.Entity.Ticket;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
@Data
@NoArgsConstructor
@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "userId")
    private int userId;
/*

    @Column(name = "ticketId")
    private int ticketId;
*/

    @ManyToOne
    @JoinColumn(name = "ticketId")
    private Ticket ticket;

    private int quantity;
    private boolean isPayment;
    private int orderid;


    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // Constructors, getters, and setters
}