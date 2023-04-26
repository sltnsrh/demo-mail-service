package com.salatin.demomailservice.impl;

import com.salatin.demomailservice.UserService;
import com.salatin.demomailservice.model.User;
import com.salatin.demomailservice.repository.UserRepository;
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
}
