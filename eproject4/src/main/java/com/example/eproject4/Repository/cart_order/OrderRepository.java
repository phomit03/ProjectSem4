package com.example.eproject4.Repository.cart_order;

import com.example.eproject4.Entity.cart_order.Cart;
import com.example.eproject4.Entity.cart_order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Tùy chỉnh các phương thức truy vấn nếu cần thiết

    //lấy giỏ hàng qua order
    @Query("SELECT c FROM Order c WHERE c.userId = :userId ")
    List<Order> findListByUserId(@Param("userId") int orderId);

    List<Order> findTop7ByOrderByCreatedAtAsc();

    @Query("SELECT DATE(o.createdAt) as orderDate, SUM(o.totalPrice) as totalAmount FROM Order o WHERE o.createdAt >= :startDate GROUP BY DATE(o.createdAt)")
    List<Object[]> getTotalAmountByDayInLast7Days(Date startDate);

    @Query("SELECT DATE(o.createdAt) as orderDate, COUNT(o) as orderCount FROM Order o WHERE o.createdAt >= :startDate GROUP BY DATE(o.createdAt)")
    List<Object[]> countOrdersInLast7DaysByDay(Date startDate);

    @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE DATE(o.createdAt) = :today")
    Double getTotalAmountOfToday(Date today);

    @Query("SELECT SUM(o.totalPrice) FROM Order o")
    Double getTotalAmountOfAllOrders();

    @Query("SELECT COUNT(o) FROM Order o WHERE DATE(o.createdAt) = CURRENT_DATE")
    Long countOrdersToday();

    @Query("SELECT COUNT(o) FROM Order o")
    Long countTotalOrders();
}
