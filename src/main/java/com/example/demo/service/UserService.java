package com.example.demo.service;

import com.example.demo.Entity.Address;
import com.example.demo.Entity.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    void deleteAllUsers();
}
