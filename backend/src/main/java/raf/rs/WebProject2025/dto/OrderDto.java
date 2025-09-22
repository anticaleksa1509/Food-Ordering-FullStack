package raf.rs.WebProject2025.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {

    @NotNull
    private Boolean active;

    @NotEmpty
    private List<String> dishes;
}
