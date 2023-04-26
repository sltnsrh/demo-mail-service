package com.salatin.demomailservice.service.impl;

import com.salatin.demomailservice.exception.EmailAlreadyExistsException;
import com.salatin.demomailservice.exception.UserNotFoundException;
import com.salatin.demomailservice.exception.UsernameAlreadyExistsException;
import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.repository.UserRepository;
import com.salatin.demomailservice.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User create(User user) {
        checkIfUsernameExists(user);
        checkIfEmailExists(user);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Can't find a user with id: " + user.getId());
        }

        checkIfUsernameExists(user);
        checkIfEmailExists(user);

        User userToUpdate = userOptional.get();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());

        return userRepository.save(userToUpdate);
    }

    private void checkIfUsernameExists(User user) {
        Optional<User> userOptional = userRepository.findByUsername(user.getUsername());

        if (userOptional.isPresent() && userOptional.get().getId() != user.getId()) {
            throw new UsernameAlreadyExistsException(
                String.format("User with username: %s is already exists", user.getUsername()));
        }
    }

    private void checkIfEmailExists(User user) {
        Optional<User> userOptional = userRepository.findByEmail(user.getEmail());

        if (userOptional.isPresent() && userOptional.get().getId() != user.getId()) {
            throw new EmailAlreadyExistsException(
                String.format("User with email: %s is already exists", user.getEmail())
            );
        }
    }
}
