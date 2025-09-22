package raf.rs.WebProject2025.service;

import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import raf.rs.WebProject2025.entities.Order;
import raf.rs.WebProject2025.entities.Status;
import raf.rs.WebProject2025.entities.User;
import raf.rs.WebProject2025.repositories.OrderRepo;
import raf.rs.WebProject2025.repositories.UserRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchOrderService {

    @Autowired
    OrderRepo orderRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    TokenService tokenService;

    public List<Order> getOrderUntilDateTime(LocalDateTime endTime,Integer id_user) throws Exception{
        User user = userRepo.findById(Long.valueOf(id_user)).orElseThrow();
        List<Order> orders = orderRepo.getOrdersByLocalDateTimeBeforeAndUser(endTime,user);
        if(orders.isEmpty())
            throw new Exception("List is empty");
        return orders;
    }

    @Transactional
    public void cancelOrder(Integer id_order, String token) throws Exception {
        String jwtToken = token.substring(7); // Uklanjamo "Bearer " iz tokena
        Claims claims = tokenService.parseToken(jwtToken);
        Integer userId = Math.toIntExact(claims.get("id_user", Long.class)); // ID korisnika iz tokena

        Order order = orderRepo.findById(Long.valueOf(id_order)).orElseThrow(() ->
                new EntityNotFoundException("Order with ID " + id_order + " does not exist!"));

        // Korisnik može otkazati samo svoje porudžbine
        if (!order.getUser().getId_user().equals(userId)) {
            throw new Exception("You can only cancel your own orders!");
        }

        // Dozvoljeno otkazivanje samo ako je ORDERED
        if (order.getStatus() == Status.ORDERED) {
            order.setStatus(Status.CANCELED);
            orderRepo.save(order);
        } else {
            throw new Exception("Only orders with ORDERED status can be canceled.");
        }
    }



    public Status trackOrderStatus(Integer id_order,Integer id_user) throws Exception{
        Order order = orderRepo.findById(Long.valueOf(id_order)).orElseThrow(()->
                new EntityNotFoundException("Order with ID that you have entered does not exist"));
        User user = userRepo.findById(Long.valueOf(id_user)).orElseThrow();
        if(order.getUser().equals(user))
                return order.getStatus();
        throw new Exception("User are allowed to see orders that they have created");
    }

    public List<Order> getOrdersBetweenDates(LocalDateTime startDate, LocalDateTime endDate,Integer id_user) throws Exception{
        User user = userRepo.findById(Long.valueOf(id_user)).orElseThrow();
        List<Order> orders = orderRepo.getOrdersByLocalDateTimeBetweenAndUser(startDate,endDate,user);
        if(orders.isEmpty())
            throw new Exception("List is empty");
        return orders;
    }

    public List<Order> getByStatus(Status status,Integer id_user) throws Exception{
        User user = userRepo.findById(Long.valueOf(id_user)).orElseThrow();
        List<Order> orders = orderRepo.getOrderByStatusAndUser(status,user);
        if(orders.isEmpty()){
            throw new Exception("List is empty");
        }
        return orders;
    }

    public List<Order> getOrders(Integer id_user) throws Exception{
        User user = userRepo.findById(Long.valueOf(id_user)).orElseThrow();
        List<Order> orders = orderRepo.getOrderByUser(user);//za specificnog korisnika
        if(user.getRole().equals("Administrator")){
            return orderRepo.findAll();//ukoliko je admin ulogovan vracamo sve porudzbine
        }
        if(orders.isEmpty())
            throw new Exception("List is empty");

        return orders;
    }

    public List<Order> getByIdUser(Integer id_user,Integer id_myUser) throws Exception{
        User userForCheck = userRepo.findById(Long.valueOf(id_user)).orElseThrow(() ->
                new EntityNotFoundException("User does not exist"));
        if(id_myUser != null) {
            User myUser = userRepo.findById(Long.valueOf(id_myUser)).orElseThrow(() ->
                    new EntityNotFoundException("User does not exist"));
            if (userForCheck.getRole().equals("Administrator")) {
                List<Order> orders = orderRepo.getOrderByUser(myUser);
                if (orders.isEmpty())
                    throw new Exception("List is empty");
                return orders;
            } else {
                throw new Exception("Only administrators are allowed");
            }
        }else {
            if(userForCheck.getRole().equals("Administrator")) {
                List<Order> orders = orderRepo.findAll();
                if (orders.isEmpty())
                    throw new Exception("Orders list is empty");
                return orders;
            }else {
                throw new Exception("This action can be provided by administrator only");
            }
        }
    }



}
