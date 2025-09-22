package raf.rs.WebProject2025.controller;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.rs.WebProject2025.annotations.CanCancelOrder;
import raf.rs.WebProject2025.annotations.CanSearchOrder;
import raf.rs.WebProject2025.annotations.CanTrackOrder;
import raf.rs.WebProject2025.entities.Order;
import raf.rs.WebProject2025.entities.Status;
import raf.rs.WebProject2025.repositories.OrderRepo;
import raf.rs.WebProject2025.service.SearchOrderService;
import raf.rs.WebProject2025.service.TokenService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class SearchOrderController {

    @Autowired
    private SearchOrderService orderService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private OrderRepo orderRepo;
    @CanSearchOrder
    @GetMapping(value = "/searchUntilDate",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchOrderUntilDate(@RequestHeader("Authorization")
            String authorization,@RequestParam LocalDateTime localDateTime){

        String token = authorization.substring(7);
        Claims claims = tokenService.parseToken(token);
        Integer id_user = claims.get("id_user",Integer.class);
        try {
            List<Order> orders = orderService.getOrderUntilDateTime(localDateTime,id_user);
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("An error has occurred");
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        orderRepo.flush(); // Osigurava da podaci budu sveži
        List<Order> orders = orderRepo.findAll();
        return ResponseEntity.ok(orders);
    }



    @CanTrackOrder
    @GetMapping(value = "/getOrderStatus",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOrderStatus(@RequestHeader("Authorization") String authorization,
                                            @RequestParam Integer id_order){
        String token = authorization.substring(7);
        Claims claims = tokenService.parseToken(token);
        Integer id_user = claims.get("id_user",Integer.class);

        try {
            Status status = orderService.trackOrderStatus(id_order,id_user);
            return ResponseEntity.status(HttpStatus.OK).body(status);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @CanSearchOrder
    @GetMapping(value = "/getBetweenDates",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOrdersBetweenDates(@RequestHeader("Authorization") String authorization,
                                                   @RequestParam LocalDateTime startTime,
                                                   @RequestParam LocalDateTime endTime){
        String token = authorization.substring(7);
        Claims claims = tokenService.parseToken(token);
        Integer id_user = claims.get("id_user",Integer.class);
        try {
            List<Order> orders = orderService.getOrdersBetweenDates(startTime,endTime,id_user);
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @CanSearchOrder
    @GetMapping(value = "/searchForUser",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchForUser(@RequestHeader("Authorization")
                                               String authorization){
        String token = authorization.substring(7);
        Claims claims = tokenService.parseToken(token);
        Integer id_user = claims.get("id_user",Integer.class);
        try {
            List<Order> orders = orderService.getOrders(id_user);
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @CanCancelOrder
    @PutMapping(value = "/cancelOrder")
    public ResponseEntity<?> cancelOrder(@RequestHeader("Authorization") String authorization,
                                         @RequestParam Integer id_order) {
        try {
            orderService.cancelOrder(id_order, authorization);
            return ResponseEntity.ok(Collections.singletonMap("message", "Order successfully canceled!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonMap("error", e.getMessage()));
        }
    }


    @CanSearchOrder
    @GetMapping(value = "/searchByStatus",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOrdersByStatus(@RequestParam Status status,
                                               @RequestHeader("Authorization") String authorization){
        String token = authorization.substring(7);
        Claims claims = tokenService.parseToken(token);
        Long id_user1 = Long.valueOf(claims.get("id_user", Integer.class));

        try {
            List<Order> orders = orderService.getByStatus(status, Math.toIntExact(id_user1));
            return ResponseEntity.status(HttpStatus.FOUND).body(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @CanSearchOrder
    @GetMapping(value = "/searchById",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getByIdUser(@RequestHeader("Authorization") String authorization,
                                         @RequestParam Long id_user){
        String token = authorization.substring(7);
        Claims claims = tokenService.parseToken(token);
        Long id_user1 = Long.valueOf(claims.get("id_user", Integer.class));
        try {
            List<Order> orders = orderService.getByIdUser(Math.toIntExact(id_user1), Math.toIntExact(id_user));
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
