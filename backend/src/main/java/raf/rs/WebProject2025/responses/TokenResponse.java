package raf.rs.WebProject2025.responses;

import lombok.Data;

@Data
public class TokenResponse {
    private String token;
    public TokenResponse(String token){
        this.token = token;
    }
}
