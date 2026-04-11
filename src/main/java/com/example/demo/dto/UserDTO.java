package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "User Data Transfer Object")
public class UserDTO {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Schema(description = "User's full name", example = "John Doe")
    private String name;

    @NotNull(message = "Age is required")
    @Min(value = 18, message = "Age must be at least 18")
    @Max(value = 60, message = "Age cannot be above 60")
    @Schema(description = "User's age", example = "25")
    private Integer age;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Schema(description = "User's email address", example = "john@example.com")
    private String email;

    @NotBlank(message = "Mobile number is required")
    @Size(min = 10, max = 10, message = "Mobile number must be 10 digits")
    @Schema(description = "User's mobile number", example = "9876543210")
    private String mobile;

    @Schema(description = "Is user active", example = "true")
    private Boolean isActive;

    @Valid
    @Schema(description = "List of user addresses")
    private List<AddressDTO> addresses;
}

