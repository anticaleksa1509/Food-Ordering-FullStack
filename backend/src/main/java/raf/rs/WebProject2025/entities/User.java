package raf.rs.WebProject2025.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import raf.rs.WebProject2025.annotations.UniqueValue;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_entity",indexes = {@Index(name = "id_user_email",columnList = "email"),@Index(name = "id_user_password",columnList = "password")})
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;

    @NotBlank(message = "Name can not be blank!")
    private String name;

    private String role;

    @NotBlank(message = "Last name can not be blank!")
    private String lastName;

    @Email(message = "Email address must be valid!")
    private String email;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @NotBlank(message = "Password can not be blank!")
    @Size(min = 8,message = "Password must have at least 8 characters!")
    private String password;

    @NotNull(message = "This permission is required!")
    private Boolean can_create;
    @NotNull(message = "This permission is required!")
    private Boolean can_read;
    @NotNull(message = "This permission is required!")
    private Boolean can_update;
    @NotNull(message = "This permission is required!")
    private Boolean can_delete;

    private Boolean can_create_order;

    @NotNull(message = "This permission is required!")
    private Boolean can_search_order;
    @NotNull(message = "This permission is required!")
    private Boolean can_track_order;
    @NotNull(message = "This permission is required!")
    private Boolean can_cancel_order;
    @NotNull(message = "This permission is required!")
    private Boolean can_schedule_order;

}
