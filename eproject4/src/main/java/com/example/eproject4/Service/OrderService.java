package com.example.eproject4.Service;

import com.example.eproject4.DTO.Response.MatchDTO;
import com.example.eproject4.DTO.Response.OrderDTO;
import com.example.eproject4.Entity.Match;
import com.example.eproject4.Entity.cart_order.Order;
import com.example.eproject4.Repository.cart_order.OrderRepository;
import com.example.eproject4.Utils.ModelToDtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private ModelToDtoConverter modelToDtoConverter;
    @Autowired
    private OrderRepository orderRepository;

    public List<OrderDTO> getOrdersForLast7Days() {
        List<Order> orders = orderRepository.findTop7ByOrderByCreatedAtAsc();
        return orders.stream().map(order -> modelToDtoConverter.convertToDto(order, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public List<Object[]> countOrdersInLast7DaysByDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date startDate = calendar.getTime();
        return orderRepository.countOrdersInLast7DaysByDay(startDate);
    }

    public List<Object[]> getTotalAmountByDayInLast7Days() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date startDate = calendar.getTime();
        return orderRepository.getTotalAmountByDayInLast7Days(startDate);
    }

    public Double getTotalAmountOfToday() {
        Date today = new Date();
        return orderRepository.getTotalAmountOfToday(today);
    }

    public Double getTotalAmountOfAllOrders() {
        return orderRepository.getTotalAmountOfAllOrders();
    }

    public Long countOrdersToday() {
        return orderRepository.countOrdersToday();
    }

    public Long countTotalOrders() {
        return orderRepository.countTotalOrders();
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    public void updateOrderStatus(int id, boolean newStatus) {
        // Tìm đơn hàng theo ID
        Order order = getOrderById(id);

        if (order != null) {
            // Cập nhật trạng thái của đơn hàng
            order.setStatus(newStatus);

            // Lưu đơn hàng đã cập nhật
            orderRepository.save(order);
        }
    }
}
