package com.example.demo.controller;

import com.example.demo.Entity.Address;
import com.example.demo.Entity.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // Read all
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Given user Id "+id + " deleted successfully!");
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteUser() {
        userService.deleteAllUsers();
        return ResponseEntity.ok("All Users deleted successfully");
    }

    @GetMapping("/{id}/addresses")
    public  ResponseEntity<List<Address>> getAddressById(@PathVariable Long id){
        User user =userService.getUserById(id);
        List<Address> addresses = user.getAddresses();

        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }


}

