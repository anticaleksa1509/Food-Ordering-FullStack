package raf.rs.WebProject2025.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raf.rs.WebProject2025.dto.OrderDto;
import raf.rs.WebProject2025.entities.Order;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final UserService userService;

    @Autowired
    private  TaskScheduler taskScheduler;

    @Autowired
    private ChangeOrderStateService orderStateService;

    @Transactional
    public void scheduleOperation(LocalDateTime scheduledDateTime, OrderDto orderDto,Integer id_user){
        //taskScheduler koristi Date tako da moramo da konvertujemo format vremena
        Date date = java.sql.Timestamp.valueOf(scheduledDateTime);
        taskScheduler.schedule(() ->{
            try {
                Order order = userService.createOrder(orderDto,id_user);
                orderStateService.changeOrderStateToPreparing(order.getId_order());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        },date);
    }

}
