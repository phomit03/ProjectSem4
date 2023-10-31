package com.example.eproject4.Controller;

import com.example.eproject4.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
    @Autowired
    private OrderService orderService;


    @RequestMapping("/admin")
    public String admin(Model model) {
        Double totalAmountOfToday = orderService.getTotalAmountOfToday();
        model.addAttribute("totalAmountOfToday", totalAmountOfToday);
        Double totalAmount = orderService.getTotalAmountOfAllOrders();
        model.addAttribute("totalAmount", totalAmount);
        Long orderTodayCount = orderService.countOrdersToday();
        model.addAttribute("orderTodayCount", orderTodayCount);
        Long totalOrderCount = orderService.countTotalOrders();
        model.addAttribute("totalOrderCount", totalOrderCount);
        return "admin_index";
    }
}
