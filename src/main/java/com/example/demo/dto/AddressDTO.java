package com.example.demo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Address Data Transfer Object")
public class AddressDTO {

    @Schema(description = "Address ID", example = "1")
    private Long id;

    @NotBlank(message = "Street is required")
    @Schema(description = "Street address", example = "123 Main Street")
    private String street;

    @NotBlank(message = "City is required")
    @Schema(description = "City name", example = "New York")
    private String city;

    @NotBlank(message = "State is required")
    @Schema(description = "State name", example = "NY")
    private String state;

    @NotBlank(message = "Country is required")
    @Schema(description = "Country name", example = "USA")
    private String country;
}

