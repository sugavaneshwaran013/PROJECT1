package com.studentportal.studentportal.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.studentportal.studentportal.model.User;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User,Long> {
    // User findByEmail(String username);
    User findByEmail(String email);

    Optional<User> findByName(String name);
}

