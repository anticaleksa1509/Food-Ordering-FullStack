package raf.rs.WebProject2025.service;

import com.mysql.cj.exceptions.NumberOutOfRange;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import raf.rs.WebProject2025.dto.OrderDto;
import raf.rs.WebProject2025.entities.Order;
import raf.rs.WebProject2025.entities.Status;
import raf.rs.WebProject2025.entities.User;
import raf.rs.WebProject2025.repositories.OrderRepo;
import raf.rs.WebProject2025.repositories.UserRepo;
import raf.rs.WebProject2025.securityAspect.HashPassword;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Validated
public class UserService {

    private final UserRepo userRepo;
    private final HashPassword hashPassword;
    private final OrderRepo orderRepo;
    private final ChangeOrderStateService orderStateService;
    private final ErrorService errorService;
    public void createUser(User user){
        user.setPassword(hashPassword.encode(user.getPassword()));
        user.setRole("User");
        user.setCan_create_order(true);
        user.setCan_search_order(true);
        userRepo.save(user);
    }

    public void deleteUser(Long id_user) {
        Optional<User> optionalUser = userRepo.findById(id_user);
        if(optionalUser.isEmpty()){
            throw new IllegalArgumentException("No user exists with that ID.");
        }
        userRepo.delete(optionalUser.get());
    }

    public List<User> getAll() throws Exception{
        List<User> users = userRepo.findAll();
        if(users.isEmpty())
            throw new Exception("List of users is empty");
        return users;
    }


   @Transactional
    public void updateUser(Long id_user,User user) throws Exception{
        Optional<User> optionalUser = userRepo.findById(id_user);
        if(optionalUser.isEmpty())
            throw new Exception("No user exists with that ID");

        User myUser = optionalUser.get();

        if(user.getName() != null && !user.getName().isBlank())
            myUser.setName(user.getName());
            //isBlank proverava samo da li je "" ili "  "(ako ima belinu) ali ne resava
            //slucaj ako string ne postoji zato moramo da uvedemo i != null
        if(user.getLastName() != null && !user.getLastName().isBlank())
            myUser.setLastName(user.getLastName());
        if(user.getEmail() != null && !user.getEmail().isBlank())
            myUser.setEmail(user.getEmail());
        if(user.getPassword() != null && !user.getPassword().isBlank()){
            if(user.getPassword().length() < 8){
                throw new IllegalArgumentException("Password must be at least size of 8");
            }
            myUser.setPassword(hashPassword.encode(user.getPassword()));
        }
        if((user.getCan_update() != null && !user.getCan_update().toString().isBlank()))
            myUser.setCan_update(user.getCan_update());
       if(user.getCan_delete() != null && !user.getCan_delete().toString().isBlank())
           myUser.setCan_delete(user.getCan_delete());
       if(user.getCan_read() != null && !user.getCan_read().toString().isBlank())
           myUser.setCan_read(user.getCan_read());
       if(user.getCan_create() != null && !user.getCan_create().toString().isBlank())
           myUser.setCan_create(user.getCan_create());

       if(user.getCan_create_order() != null && !user.getCan_create_order().toString().isBlank())
           myUser.setCan_create_order(user.getCan_create_order());
       if(user.getCan_cancel_order() != null && !user.getCan_cancel_order().toString().isBlank())
           myUser.setCan_cancel_order(user.getCan_cancel_order());
       if(user.getCan_schedule_order() != null && !user.getCan_schedule_order().toString().isBlank())
           myUser.setCan_schedule_order(user.getCan_schedule_order());
       if(user.getCan_track_order() != null && !user.getCan_track_order().toString().isBlank())
           myUser.setCan_track_order(user.getCan_track_order());
        userRepo.save(myUser);
    }
    public int getLastID(){

        int last = 0;
        List<Order> orders = orderRepo.findAll();
        for(Order o : orders) {
             last = Math.toIntExact(o.getId_order());
        }
        return last;
    }


    public Order createOrder(OrderDto orderDto, Integer idUser) throws Exception {

        if(hasReachedLimit()){

            String message = "Limit has been reached";
            int id_order = getLastID() + 1;
            errorService.saveErrorMessage(message,id_order,idUser);

            throw new Exception("Can not create order because limit has been reached!");
        }
        Order order = new Order();
        User user = userRepo.findById(Long.valueOf(idUser)).orElseThrow(()->
                new EntityNotFoundException("User can not be found!"));

        Hibernate.initialize(user.getOrders());
        Hibernate.initialize(order.getDishes());
        order.setUser(user);
        order.setActive(true);//aktivna je porudzbina kada je kreiram
        order.setStatus(Status.ORDERED);
        order.getDishes().addAll(orderDto.getDishes());
        order.setLocalDateTime(LocalDateTime.now());
        orderRepo.save(order);
        user.getOrders().add(order);
        userRepo.save(user);

        //asinhrona promena stanja
        orderStateService.changeOrderStateToPreparing(order.getId_order());
        return order;
    }

    public boolean hasReachedLimit(){
        long prepairingOrderCount = orderRepo.countOrderByStatus(Status.PREPAIRING);
        long in_delieveryCount = orderRepo.countOrderByStatus(Status.IN_DELIVERY);

        long totalCount = prepairingOrderCount + in_delieveryCount;
        if(totalCount >= 3)
            return true;
        return false;
    }


}
