package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    //  --------  1. Derived Query Methods (No SQL/JPQL needed)-----------
    //Spring Data automatically creates queries based on method names.

    // Find by username
//    User findByUserName(String userName);

    // Find users by email containing (like %email%)
//    User findByEmailContaining(String email);

    // Find users with age greater than given value
//    List<User> findByAgeEquals(int age);

    //  -------- 2. JPQL / HQL Queries (@Query) ------------
    //If you need custom JPQL (entity-based queries):
//    @Query("SELECT u FROM User u WHERE u.username = :username")
//    List<User> findUserByUsername(@Param("username") String username);

//    @Query("SELECT u FROM User u WHERE u.age < :age")
//    List<User> getUsersBelowAge(@Param("age") int age);

    //  -------- 3. Native SQL Queries
    //If you need raw SQL (database-specific):
//    @Query(value = "SELECT * FROM users u WHERE u.email = :email", nativeQuery = true)
//    User findByEmailNative(@Param("email") String email);


}
