package raf.rs.WebProject2025.controller;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import raf.rs.WebProject2025.entities.ErrorMessage;
import raf.rs.WebProject2025.service.ErrorService;
import raf.rs.WebProject2025.service.TokenService;

import java.util.List;

@RestController
@RequestMapping("/error")
public class ErrorController {
    @Autowired
    ErrorService errorService;
    @Autowired
    TokenService tokenService;

    @GetMapping(value = "/messages",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMessages(@RequestParam String token){

        Claims claims = tokenService.parseToken(token);
        Integer idUser = claims.get("id_user", Integer.class);

        try {
            List<ErrorMessage> messages = errorService.getMessages(idUser);
            return ResponseEntity.status(HttpStatus.OK).body(messages);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
