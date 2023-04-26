package com.salatin.demomailservice.service.impl;

import com.salatin.demomailservice.exception.UserNotFoundException;
import com.salatin.demomailservice.service.UserService;
import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("Can't find a user with id: " + user.getId());
        }

        User userToUpdate = userOptional.get();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());

        return userRepository.save(userToUpdate);
    }
}
