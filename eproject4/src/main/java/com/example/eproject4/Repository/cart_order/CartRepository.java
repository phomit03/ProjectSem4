package com.example.eproject4.Repository.cart_order;

import com.example.eproject4.Entity.cart_order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    // Tùy chỉnh các phương thức truy vấn nếu cần thiết

    //lấy giỏ hàng chưa thanh tpnaf
    @Query("SELECT c FROM Cart c WHERE c.userId = :userId AND c.isPayment = false")
    List<Cart> findByUserIdAndStatusFalse(@Param("userId") int userId);

    //lấy giỏ hàng qua order
    @Query("SELECT c FROM Cart c WHERE c.orderid = :orderId ")
    List<Cart> findByOrder(@Param("orderId") int orderId);
}