package com.salatin.demomailservice.service.impl;

import com.salatin.demomailservice.exception.EmailAlreadyExistsException;
import com.salatin.demomailservice.exception.UserNotFoundException;
import com.salatin.demomailservice.exception.UsernameAlreadyExistsException;
import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.repository.UserRepository;
import com.salatin.demomailservice.service.UserService;
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
        checkIfUserExists(user.getId());
        checkIfUsernameExists(user);
        checkIfEmailExists(user);

        User userToUpdate = userRepository.findById(user.getId()).orElseThrow();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());

        return userRepository.save(userToUpdate);
    }

    @Override
    public void delete(Integer id) {
        checkIfUserExists(id);

        userRepository.deleteById(id);
    }

    private void checkIfUserExists(Integer id) {

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Can't find a user with id: " + id);
        }
    }

    private void checkIfUsernameExists(User user) {
        var userOptional = userRepository.findByUsername(user.getUsername());
        var usernameBelongsAnotherUser =
            userOptional.isPresent() && userOptional.get().getId() != user.getId();

        if (usernameBelongsAnotherUser) {
            throw new UsernameAlreadyExistsException(
                String.format("User with username: %s is already exists", user.getUsername()));
        }
    }

    private void checkIfEmailExists(User user) {
        var userOptional = userRepository.findByEmail(user.getEmail());
        var emailBelongsAnotherUser =
            userOptional.isPresent() && userOptional.get().getId() != user.getId();

        if (emailBelongsAnotherUser) {
            throw new EmailAlreadyExistsException(
                String.format("User with email: %s is already exists", user.getEmail())
            );
        }
    }
}
