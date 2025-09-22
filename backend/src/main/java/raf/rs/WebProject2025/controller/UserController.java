package raf.rs.WebProject2025.controller;

import com.mysql.cj.exceptions.NumberOutOfRange;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import raf.rs.WebProject2025.annotations.*;
import raf.rs.WebProject2025.dto.OrderDto;
import raf.rs.WebProject2025.entities.User;
import raf.rs.WebProject2025.repositories.OrderRepo;
import raf.rs.WebProject2025.service.TokenService;
import raf.rs.WebProject2025.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final OrderRepo orderRepo;

    @GetMapping(value = "/role")
    public ResponseEntity<?> getRole(@RequestHeader("Authorization")String authorization){
        String token = authorization.substring(7);
        Claims claims = tokenService.parseToken(token);
        String role =  claims.get("role", String.class);
        return ResponseEntity.ok(role);
    }


    @CanCreateOrder
    @PostMapping(value = "/createOrder")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDto orderDto,@RequestHeader("Authorization") String authorization
                                         ){
        String token = authorization.substring(7);
        Claims claims = tokenService.parseToken(token);
        Integer id_user = claims.get("id_user", Integer.class);

        try {
            userService.createOrder(orderDto,id_user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Successfully created");
            return ResponseEntity.ok(Map.of("status", 200, "message", "Successfully created"));
        }
        catch (Exception e){
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @CanCreateUser
    @UniqueValue
    @PostMapping(value = "/create",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createNewUser(@Valid @RequestBody User user,@RequestHeader("Authorization")
        String authorization){
        try {
            userService.createUser(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Successfully created");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

    }

    @CanDeleteUser
    @DeleteMapping(value = "/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestParam Long id_user,@RequestHeader("Authorization")
            String authorization){
        try {
            userService.deleteUser(id_user);
            return ResponseEntity.status(HttpStatus.OK).body("Successfully deleted user with ID + " + id_user);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/deleteUserAction")
    public ResponseEntity<?> deleteUserNoAuth(@RequestParam Long id_user){
        try {
            userService.deleteUser(id_user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Successfully deleted user with ID " + id_user);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @CanUpdateUser
    @UniqueValue
    @PutMapping(value = "/updateUser",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@RequestParam Long id_user, @RequestBody User user,@RequestHeader("Authorization")
    String authorization){
        try {
            userService.updateUser(id_user,user);
            Map<String, String> response = new HashMap<>();
            response.put("message","Successfully updated user");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (IllegalArgumentException e){
            Map<String, String> response = new HashMap<>();
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e){
            Map<String, String> response = new HashMap<>();
            response.put("message",e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @CanReadUser
    @GetMapping(value = "/allUsers",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllUsers(@RequestHeader("Authorization")
                                              String authorization){
        try {
            List<User> users = userService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }

    @GetMapping(value = "/getAllUsers",produces = MediaType.APPLICATION_JSON_VALUE)
    //metoda koja vraca sve korisnike za zahtev fronta
    public ResponseEntity<?> getAll(){
        try {
            List<User> users = userService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }


}
