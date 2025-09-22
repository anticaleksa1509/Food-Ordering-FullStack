package raf.rs.WebProject2025.controller;

import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.rs.WebProject2025.annotations.CanScheduleOrder;
import raf.rs.WebProject2025.dto.OrderDto;
import raf.rs.WebProject2025.service.ScheduleService;
import raf.rs.WebProject2025.service.TokenService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private TokenService tokenService;
    @CanScheduleOrder
    @PostMapping(value = "/createOrder")
    public ResponseEntity<Map<String, String>> scheduleCreateOrder(@RequestParam LocalDateTime localDateTime,
                                                                   @RequestBody @Valid OrderDto orderDto,
                                                                   @RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        Claims claims = tokenService.parseToken(token);
        Integer id_user = claims.get("id_user", Integer.class);

        scheduleService.scheduleOperation(localDateTime, orderDto, id_user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Order creation scheduled!");
        return ResponseEntity.ok(response);
    }
}
