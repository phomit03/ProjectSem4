package com.example.eproject4.Entity.cart_order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket1 {
    @JsonProperty("ticket_id")
    private Long ticketId;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("name")
    private String name;
    @JsonProperty("price")
    private int price;

    @JsonProperty("homeName")
    private String homeTeamName;

    @JsonProperty("awayName")
    private String awayTeamName;

    @JsonProperty("totalQuantity")
    private int totalQuantity;

    @JsonProperty("matchTime")
    private String matchTime;

    @JsonProperty("originPrice")
    private int originPrice;

}