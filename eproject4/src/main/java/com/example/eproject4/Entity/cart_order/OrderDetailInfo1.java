package com.example.eproject4.Entity.cart_order;

import com.example.eproject4.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailInfo1 {
    private int orderId;
    private User user;
    private Float totalPrice;
    private String qrcode;
    private List<TicketDetailInfo> listTicket;

}
