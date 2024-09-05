package com.api.model.request;

import com.api.model.entity.Product;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductRequest {
    @NotEmpty(message = "Name Can not be empty")
    @NotBlank(message = "Name Can not be blank")
    @NotNull(message = "Name Can not be null")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Username allow only character")
    private String name;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.05", message = "Price must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Invalid unit price format. Maximum 10 integer digits and 2 decimal digits allowed.")
    private Float price;

    public Product toEntity() {
        return new Product(null,name, price);
    }

    public Product toEntity(Long id) {
        return new Product(id,name, price);
    }
}
