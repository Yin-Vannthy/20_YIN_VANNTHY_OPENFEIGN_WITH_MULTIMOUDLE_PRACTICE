package com.api.model.request;

import com.api.model.entity.Customer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerRequest {
    @NotEmpty(message = "Name Can not be empty")
    @NotBlank(message = "Name Can not be blank")
    @NotNull(message = "Name Can not be null")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Username allow only character")
    private String name;

    @NotEmpty(message = "Email Can not be empty")
    @NotBlank(message = "Email Can not be blank")
    @NotNull(message = "Email Can not be null")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Invalid email address")
    private String email;

    public Customer toEntity() {
        return new Customer(null,name, email.toLowerCase());
    }

    public Customer toEntity(Long id) {
        return new Customer(id,name, email.toLowerCase());
    }
}
