package raf.rs.WebProject2025.controller;

import jakarta.validation.Valid;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import raf.rs.WebProject2025.dto.LoginCredentials;
import raf.rs.WebProject2025.responses.TokenResponse;
import raf.rs.WebProject2025.service.LoginService;
import raf.rs.WebProject2025.service.TokenService;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private LoginService loginService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginCredentials loginCredentials){
        try {
            TokenResponse token = loginService.loginUser(loginCredentials);
            return ResponseEntity.status(HttpStatus.OK).body(token);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
