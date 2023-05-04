package com.salatin.demomailservice.service;

import com.salatin.demomailservice.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {

    User create(User user);

    User update(User user);

    User findById(Integer id);
    void delete(Integer id);

    Page<User> findAll(PageRequest pageRequest);

    User findByUsername(String username);

    User findByEmail(String email);
}
