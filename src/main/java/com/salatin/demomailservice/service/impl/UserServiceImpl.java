package com.salatin.demomailservice.service.impl;

import com.salatin.demomailservice.exception.EmailAlreadyExistsException;
import com.salatin.demomailservice.exception.UserNotFoundException;
import com.salatin.demomailservice.exception.UsernameAlreadyExistsException;
import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.repository.UserRepository;
import com.salatin.demomailservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() ->
            new UserNotFoundException("Can't find a user with id: " + id));
    }

    @Override
    public void delete(Integer id) {
        checkIfUserExists(id);

        userRepository.deleteById(id);
    }

    @Override
    public Page<User> findAll(PageRequest pageRequest) {

        return userRepository.findAll(pageRequest);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsernameIgnoreCase(username.trim())
            .orElseThrow(() ->
                new UserNotFoundException("Can't find a user with username: " + username));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email.trim()).orElseThrow(() ->
            new UserNotFoundException("Can't find a user with email: " + email));
    }

    private void checkIfUserExists(Integer id) {

        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("Can't find a user with id: " + id);
        }
    }

    private void checkIfUsernameExists(User user) {
        var userOptional = userRepository.findByUsernameIgnoreCase(user.getUsername());
        var usernameBelongsAnotherUser =
            userOptional.isPresent() && userOptional.get().getId() != user.getId();

        if (usernameBelongsAnotherUser) {
            throw new UsernameAlreadyExistsException(
                String.format("User with username: %s is already exists", user.getUsername()));
        }
    }

    private void checkIfEmailExists(User user) {
        var userOptional = userRepository.findByEmailIgnoreCase(user.getEmail());
        var emailBelongsAnotherUser =
            userOptional.isPresent() && userOptional.get().getId() != user.getId();

        if (emailBelongsAnotherUser) {
            throw new EmailAlreadyExistsException(
                String.format("User with email: %s is already exists", user.getEmail())
            );
        }
    }
}
