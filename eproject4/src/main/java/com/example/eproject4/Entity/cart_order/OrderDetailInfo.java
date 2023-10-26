package com.example.eproject4.Entity.cart_order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailInfo {
    private Float totalPrice;
    private String qrcode;
    private List<TicketDetailInfo> listTicket;

}
