package com.example.eproject4.Service;

import com.example.eproject4.DTO.Response.AreaDTO;
import com.example.eproject4.Entity.Area;
import com.example.eproject4.Entity.Role;
import com.example.eproject4.Entity.cart_order.Order;
import com.example.eproject4.Repository.AreaRepository;
import com.example.eproject4.Repository.cart_order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.eproject4.Utils.ModelToDtoConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private final ModelToDtoConverter modelToDtoConverter;

    @Autowired
    public OrderService(OrderRepository orderRepository, ModelToDtoConverter modelToDtoConverter) {
        this.orderRepository = orderRepository;
        this.modelToDtoConverter = modelToDtoConverter;
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
