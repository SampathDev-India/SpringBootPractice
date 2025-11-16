package com.example.demo.service;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    @Transactional
    public User updateUser(Long id, User userDetails) {

        // 1. Fetch existing user (or throw if not found)
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        // 2. Update fields
        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setMobile(userDetails.getMobile());
        existingUser.setIsActive(userDetails.getIsActive());

        // 3. Save updated entity
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }


//    @Override
//    public User findByUserName(String userName) {
//        return userRepository.findByUsername(userName);
//    }
//
//    @Override
//    public List<User> findByAgeEquals(int age) {
//        return userRepository.findByAgeEquals(age);
//    }
//
//    @Override
//    public User findByEmailContaining(String email) {
//        return userRepository.findByEmailContaining(email);
//    }
//
//    @Override
//    public List<User> findUserByUsername(String userName) {
//        return userRepository.findUserByUsername( userName);
//    }


}
