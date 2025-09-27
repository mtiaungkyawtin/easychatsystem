package com.binary.easychatsystem.service.impl;

import com.binary.easychatsystem.model.User;
import com.binary.easychatsystem.repository.UserRepository;
import com.binary.easychatsystem.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        // Validate username uniqueness
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists: " + user.getUsername());
        }

        // Set timestamps (will be handled by @PrePersist, but we can set lastSeen)
        user.setLastSeen(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long userId, User userDetails) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Update fields if provided
        if (userDetails.getUsername() != null && !userDetails.getUsername().equals(existingUser.getUsername())) {
            // Check if new username is unique
            if (userRepository.findByUsername(userDetails.getUsername()).isPresent()) {
                throw new RuntimeException("Username already exists: " + userDetails.getUsername());
            }
            existingUser.setUsername(userDetails.getUsername());
        }

        if (userDetails.getFullName() != null) {
            existingUser.setFullName(userDetails.getFullName());
        }

        if (userDetails.getLastSeen() != null) {
            existingUser.setLastSeen(userDetails.getLastSeen());
        }

        // @PreUpdate will handle updatedAt automatically
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        userRepository.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    // Additional utility method
    public User updateLastSeen(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        user.setLastSeen(LocalDateTime.now());
        return userRepository.save(user);
    }
}
