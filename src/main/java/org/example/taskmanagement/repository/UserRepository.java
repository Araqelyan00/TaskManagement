package org.example.taskmanagement.repository;

import org.example.taskmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
//    List<User> findTop10ByNameOrderByIdDesc(String name);
}
