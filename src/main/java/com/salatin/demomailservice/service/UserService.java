package com.salatin.demomailservice.service;

import com.salatin.demomailservice.model.User;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface UserService {

    User create(User user);

    User update(User user);

    User findById(Integer id);
    void delete(Integer id);

    Page<User> findAll(PageRequest pageRequest);

    List<User> findAll();

    User findByUsername(String username);

    User findByEmail(String email);
}
