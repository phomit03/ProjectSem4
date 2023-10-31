package com.example.eproject4.Repository.cart_order;

import com.example.eproject4.Entity.cart_order.Cart;
import com.example.eproject4.Entity.cart_order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Tùy chỉnh các phương thức truy vấn nếu cần thiết

    //lấy giỏ hàng qua order
    @Query("SELECT c FROM Order c WHERE c.userId = :userId ")
    List<Order> findListByUserId(@Param("userId") int orderId);

    List<Order> findTop7ByOrderByCreatedAtAsc();
}
