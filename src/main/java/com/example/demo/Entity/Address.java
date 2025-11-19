package com.example.demo.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Country is required")
    private String country;

    // MANY ADDRESSES BELONG TO ONE USER
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")  // FK column
//    private User user;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
