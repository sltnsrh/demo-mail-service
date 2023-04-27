package com.salatin.demomailservice.service;

import com.salatin.demomailservice.model.User;

public interface UserService {

    User create(User user);

    User update(User user);

    void delete(Integer id);
}
