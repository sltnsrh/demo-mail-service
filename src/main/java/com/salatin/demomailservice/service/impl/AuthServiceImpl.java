package com.salatin.demomailservice.service.impl;

import com.salatin.demomailservice.exception.EmailAlreadyExistsException;
import com.salatin.demomailservice.exception.UsernameAlreadyExistsException;
import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.repository.UserRepository;
import com.salatin.demomailservice.service.AuthService;
import com.salatin.demomailservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public User register(User user) {
        checkIfUsernameExists(user.getUsername());
        checkIfEmailExists(user.getEmail());

        return userService.save(user);
    }

    private void checkIfUsernameExists(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(
                String.format("User with username: %s is already exists", username));
        }
    }

    private void checkIfEmailExists(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException(
                String.format("User with email: %s is already exists", email)
            );
        }
    }
}
