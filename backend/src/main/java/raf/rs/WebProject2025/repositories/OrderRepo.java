package raf.rs.WebProject2025.repositories;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import raf.rs.WebProject2025.entities.Order;
import raf.rs.WebProject2025.entities.Status;
import raf.rs.WebProject2025.entities.User;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {


    List<Order> getOrderByStatusAndUser(Status status,User user);
    List<Order> getOrderByUser(User user);

    List<Order> getOrdersByLocalDateTimeBeforeAndUser(LocalDateTime startDate,User user);
    List<Order> getOrdersByLocalDateTimeBetweenAndUser(LocalDateTime startDate,LocalDateTime endDate,User user);

    List<Order> findOrderByStatus(Status status);
    long countOrderByStatus(Status status);


}
