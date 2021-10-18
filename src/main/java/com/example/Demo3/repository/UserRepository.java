package com.example.Demo3.repository;

import com.example.Demo3.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserEmail(String userEmail);

    boolean existsByUserEmail(String userEmail);
}
