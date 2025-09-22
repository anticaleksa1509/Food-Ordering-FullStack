    package raf.rs.WebProject2025.entities;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.NotNull;
    import lombok.Data;

    import java.time.LocalDateTime;

    @Entity
    @Data
    public class ErrorMessage {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private LocalDateTime localDateTime;
        @NotNull
        private String message;
        private String operation;
        private Long order_id;

        //@Transient
        private Long idUser;//za koga se dogodila greska

    }
