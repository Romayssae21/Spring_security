package com.Romayssae.Security.repositories;

import com.Romayssae.Security.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface Repository extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}
