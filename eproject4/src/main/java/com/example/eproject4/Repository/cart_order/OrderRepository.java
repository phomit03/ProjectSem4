package com.example.eproject4.Repository.cart_order;

import com.example.eproject4.Entity.cart_order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Tùy chỉnh các phương thức truy vấn nếu cần thiết
}
