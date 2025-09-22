package raf.rs.WebProject2025.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.rs.WebProject2025.entities.Order;
import raf.rs.WebProject2025.entities.Status;
import raf.rs.WebProject2025.repositories.OrderRepo;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class ChangeOrderStateService {

    @Autowired
    private OrderRepo orderRepo;



    @Async("taskExecutor")
   // @Transactional
    public void changeOrderStateToPreparing(Long orderId) throws InterruptedException {
        System.out.println("Usao");
        Thread.sleep((10 + new Random().nextInt(5)) * 1000);
        //vremenska devijacija po specifikaciji
        // Simulira kašnjenje u sekundama
        updateOrderStatus(orderId, Status.PREPAIRING);
        changeOrderStateToInDelivery(orderId);
    }

   // @Scheduled(fixedRate = 7000)
    @Async("taskExecutor")
    //@Transactional
    public void changeOrderStateToInDelivery(Long orderId) throws InterruptedException {
        Thread.sleep((15 + new Random().nextInt(5)) * 1000); // Simulira kašnjenje u sekundama
        updateOrderStatus(orderId, Status.IN_DELIVERY);
        changeOrderStateToDelivered(orderId);
    }

   // @Scheduled(fixedRate = 7000)
    @Async("taskExecutor")
    //@Transactional
    public void changeOrderStateToDelivered(Long orderId) throws InterruptedException {
        Thread.sleep((20 + new Random().nextInt(5)) * 1000); // Simulira kašnjenje u sekundama
        updateOrderStatus(orderId, Status.DELIEVERED);
    }



   // @Transactional
    public void updateOrderStatus(Long orderId, Status newStatus) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found!"));

        if (isValidStatusTransition(order.getStatus(), newStatus)) {
            orderRepo.flush();
            order.setStatus(newStatus);
            orderRepo.save(order);
            orderRepo.flush();
            System.out.println("Order " + orderId + " status updated to " + newStatus);
        }
    }

   // @Transactional
    public boolean isValidStatusTransition(Status currentStatus, Status newStatus) {
        return (currentStatus == Status.ORDERED && newStatus == Status.PREPAIRING) ||
                (currentStatus == Status.PREPAIRING && newStatus == Status.IN_DELIVERY) ||
                (currentStatus == Status.IN_DELIVERY && newStatus == Status.DELIEVERED);
    }


}
