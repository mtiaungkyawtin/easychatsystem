package com.binary.easychatsystem.service;

import com.binary.easychatsystem.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);
    Optional<User> getUserById(Long userId);
    Optional<User> getUserByUsername(String username);
    List<User> getAllUsers();
    User updateUser(Long userId, User userDetails);
    void deleteUser(Long userId);
    boolean existsByUsername(String username);
}
