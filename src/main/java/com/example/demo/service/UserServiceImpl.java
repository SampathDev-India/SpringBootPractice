package com.example.demo.service;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Entity.User;
import com.example.demo.Entity.Address;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(User user) {
        // Ensure user has no ID (for new record)
        user.setId(null);

        // Ensure all addresses are associated with this user and have no ID
        if (user.getAddresses() != null && !user.getAddresses().isEmpty()) {
            user.getAddresses().forEach(address -> {
                address.setId(null);  // Ensure new address
                address.setUser(user);  // Associate with user
            });
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAllWithAddresses();
    }

//    @Cacheable(value = "users", key = "#id")
    @Override
    @Transactional
    public User getUserById(Long id) {
        System.out.println("Fetching from DB...");
        return userRepository.findByIdWithAddresses(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

//    @CachePut(value = "users", key = "#user.id")
    @Override
    @Transactional
    public User updateUser(Long id, User userDetails) {

        // 1. Fetch existing user (or throw if not found)
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // 2. Update fields
        existingUser.setName(userDetails.getName());
        existingUser.setAge(userDetails.getAge());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setMobile(userDetails.getMobile());
        existingUser.setIsActive(userDetails.getIsActive());

        // 3. Handle addresses update
        if (userDetails.getAddresses() != null) {
            // Clear existing addresses
            existingUser.getAddresses().clear();

            // Add new addresses
            userDetails.getAddresses().forEach(address -> {
                address.setId(null);  // Ensure new address record
                address.setUser(existingUser);  // Associate with user
                existingUser.getAddresses().add(address);
            });
        }

        // 4. Save updated entity
        return userRepository.save(existingUser);
    }

//    @CacheEvict(value = "users", key = "#id")
    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}
