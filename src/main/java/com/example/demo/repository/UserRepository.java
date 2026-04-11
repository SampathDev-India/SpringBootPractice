package com.example.demo.repository;

import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    // Fetch user with addresses eagerly to avoid lazy loading issues
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.addresses WHERE u.id = :id")
    Optional<User> findByIdWithAddresses(@Param("id") Long id);

    // Fetch all users with addresses eagerly
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.addresses")
    List<User> findAllWithAddresses();

}
