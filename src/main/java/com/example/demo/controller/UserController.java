package com.example.demo.controller;

import com.example.demo.Entity.Address;
import com.example.demo.Entity.User;
import com.example.demo.dto.AddressDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing users and their addresses")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided details")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        try {
            User user = convertDTOToEntity(userDTO);
            User savedUser = userService.createUser(user);
            return ResponseEntity.ok(convertEntityToDTO(savedUser));
        } catch (Exception e) {
            throw new RuntimeException("Error creating user: " + e.getMessage(), e);
        }
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves a list of all users")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            List<UserDTO> userDTOs = users.stream()
                    .map(this::convertEntityToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(userDTOs);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving users: " + e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieves a specific user by their ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return ResponseEntity.ok(convertEntityToDTO(user));
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user: " + e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Updates an existing user's information")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            User user = convertDTOToEntity(userDTO);
            User updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(convertEntityToDTO(updatedUser));
        } catch (Exception e) {
            throw new RuntimeException("Error updating user: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by ID", description = "Deletes a specific user by their ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("Given user Id "+id + " deleted successfully!");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/deleteAll")
    @Operation(summary = "Delete all users", description = "Deletes all users from the database")
    @ApiResponse(responseCode = "200", description = "All users deleted successfully")
    public ResponseEntity<String> deleteAllUsers() {
        try {
            userService.deleteAllUsers();
            return ResponseEntity.ok("All Users deleted successfully");
        } catch (Exception e) {
            throw new RuntimeException("Error deleting all users: " + e.getMessage(), e);
        }
    }

    @GetMapping("/{id}/addresses")
    @Operation(summary = "Get user addresses", description = "Retrieves all addresses associated with a specific user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Addresses retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<List<AddressDTO>> getAddressById(@PathVariable Long id){
        try {
            User user = userService.getUserById(id);

            // Handle null or empty addresses
            if (user.getAddresses() == null || user.getAddresses().isEmpty()) {
                return ResponseEntity.ok(new ArrayList<>());
            }

            List<AddressDTO> addressDTOs = user.getAddresses().stream()
                    .map(this::convertAddressEntityToDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(addressDTOs);
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving addresses: " + e.getMessage(), e);
        }
    }

    // Helper methods for DTO conversion
    private UserDTO convertEntityToDTO(User user) {
        if (user == null) return null;

        List<AddressDTO> addressDTOs = user.getAddresses() != null
            ? user.getAddresses().stream()
                .map(this::convertAddressEntityToDTO)
                .collect(Collectors.toList())
            : null;

        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .isActive(user.getIsActive())
                .addresses(addressDTOs)
                .build();
    }

    private User convertDTOToEntity(UserDTO userDTO) {
        if (userDTO == null) return null;

        // For new user creation, don't set the ID (let DB generate it)
        // For updates, the ID will be managed separately
        User user = User.builder()
                .id(null)  // Always null for new records
                .name(userDTO.getName())
                .age(userDTO.getAge())
                .email(userDTO.getEmail())
                .mobile(userDTO.getMobile())
                .isActive(userDTO.getIsActive() != null ? userDTO.getIsActive() : true)
                .addresses(new ArrayList<>())  // Initialize with empty list
                .build();

        // Convert and attach addresses
        if (userDTO.getAddresses() != null && !userDTO.getAddresses().isEmpty()) {
            List<Address> addresses = userDTO.getAddresses().stream()
                    .map(addressDTO -> convertAddressDTOToEntity(addressDTO, user))
                    .collect(Collectors.toList());
            user.setAddresses(addresses);
        }

        return user;
    }

    private AddressDTO convertAddressEntityToDTO(Address address) {
        if (address == null) return null;

        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .build();
    }

    private Address convertAddressDTOToEntity(AddressDTO addressDTO, User user) {
        if (addressDTO == null) return null;

        // For new addresses, don't set the ID (let DB generate it)
        // For updates, the ID will be managed separately
        return Address.builder()
                .id(null)  // Always null for new records
                .street(addressDTO.getStreet())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .country(addressDTO.getCountry())
                .user(user)  // Set the user reference
                .build();
    }
}

