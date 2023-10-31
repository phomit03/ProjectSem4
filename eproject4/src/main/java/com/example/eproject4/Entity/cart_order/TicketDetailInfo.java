package com.example.eproject4.Entity.cart_order;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDetailInfo {
    private String name_home;
    private String image_home;
    private String name_away;
    private String image_away;
    private String day;
    private String hour;
    private Float price;
    private String area;
    private String stadium;

    //check het han
    private LocalDateTime time;

}
