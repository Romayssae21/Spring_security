package com.Romayssae.Security.Repository;

import com.Romayssae.Security.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface repositories extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String email);
}
