package raf.rs.WebProject2025.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_order;

    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    private Boolean active;

    @ElementCollection
    @NotEmpty
    private List<String> dishes = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    private User user;

    private LocalDateTime localDateTime;



}
