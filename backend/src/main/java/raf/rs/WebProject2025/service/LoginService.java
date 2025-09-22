package raf.rs.WebProject2025.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import raf.rs.WebProject2025.dto.LoginCredentials;
import raf.rs.WebProject2025.entities.User;
import raf.rs.WebProject2025.repositories.UserRepo;
import raf.rs.WebProject2025.responses.TokenResponse;
import raf.rs.WebProject2025.securityAspect.HashPassword;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepo userRepo;
    private final HashPassword hashPassword;
    private final TokenService tokenService;
    public TokenResponse loginUser(LoginCredentials loginCredentials){
        String password = hashPassword.encode(loginCredentials.getPassword());
        Optional<User> optionalUser = userRepo.findUserByEmailAndPassword(loginCredentials.getEmail(),password);
        if(optionalUser.isEmpty())
            throw new IllegalArgumentException("Email or password are incorrect");
        User user = optionalUser.get();
        System.out.println(user);

        Claims claims = Jwts.claims();

        claims.put("id_user",user.getId_user());
        claims.put("role",user.getRole());
        claims.put("can_create",user.getCan_create());
        claims.put("can_read",user.getCan_read());
        claims.put("can_update",user.getCan_update());
        claims.put("can_delete",user.getCan_delete());

        claims.put("can_create_order",user.getCan_create_order());
        claims.put("can_search_order",user.getCan_search_order());
        claims.put("can_cancel_order",user.getCan_cancel_order());
        claims.put("can_track_order",user.getCan_track_order());
        claims.put("can_schedule_order",user.getCan_schedule_order());
        return new TokenResponse(tokenService.generateToken(claims));

    }
}
