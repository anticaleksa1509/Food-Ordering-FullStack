package raf.rs.WebProject2025.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class ErrorLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; // ID korisnika koji je pokušao operaciju
    private String message; // Tekst greške
    private LocalDateTime timestamp; // Vreme kada se greška dogodila

    public ErrorLog(Long userId, String message) {
        this.userId = userId;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorLog() {}
}
